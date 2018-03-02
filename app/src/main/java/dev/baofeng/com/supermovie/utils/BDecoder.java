package dev.baofeng.com.supermovie.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 解析bt种子
 */
@SuppressWarnings(value = { "rawtypes", "unchecked", "unused" })
public class BDecoder {
	public static Charset BYTE_CHARSET = Charset.forName("UTF-8");;
	public static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");;
	private boolean recovery_mode;
	public static Map decode(byte[] data) throws IOException {
		return (new BDecoder().decodeByteArray(data));
	}
	public static Map decode(BufferedInputStream is) throws IOException {
		return (new BDecoder().decodeStream(is));
	}
	public BDecoder() {
	}
	public Map decodeByteArray(byte[] data) throws IOException {
		return (decode(new ByteArrayInputStream(data)));
	}
	public Map decodeStream(BufferedInputStream data) throws IOException {
		Object res = decodeInputStream(data, 0);
		if (res == null) {
			throw (new IOException("BDecoder: zero length file"));
		} else if (!(res instanceof Map)) {
			throw (new IOException("BDecoder: top level isn't a Map"));
		}
		return ((Map) res);
	}
	private Map decode(ByteArrayInputStream data) throws IOException {
		Object res = decodeInputStream(data, 0);

		if (res == null) {
			throw (new IOException("BDecoder: zero length file"));
		} else if (!(res instanceof Map)) {
			throw (new IOException("BDecoder: top level isn't a Map"));
		}

		return ((Map) res);
	}

	private Object decodeInputStream(InputStream bais, int nesting)

	throws IOException {
		if (nesting == 0 && !bais.markSupported()) {
			throw new IOException("InputStream must support the mark() method");
		}
		// set a mark
		bais.mark(Integer.MAX_VALUE);
		// read a byte
		int tempByte = bais.read();
		// decide what to do
		switch (tempByte) {
		case 'd':
			// create a new dictionary object
			Map tempMap = new HashMap();
			try {
				// get the key
				byte[] tempByteArray = null;
				while ((tempByteArray = (byte[]) decodeInputStream(bais, nesting + 1)) != null) {
					// decode some more
					Object value = decodeInputStream(bais, nesting + 1);
					// add the value to the map
					CharBuffer cb = BYTE_CHARSET.decode(ByteBuffer.wrap(tempByteArray));
					String key = new String(cb.array(), 0, cb.limit());
					tempMap.put(key, value);
				}
				bais.mark(Integer.MAX_VALUE);
				tempByte = bais.read();
				bais.reset();
				if (nesting > 0 && tempByte == -1) {
					throw (new IOException("BDecoder: invalid input data, 'e' missing from end of dictionary"));
				}
			} catch (Throwable e) {
				if (!recovery_mode) {
					if (e instanceof IOException) {
						throw ((IOException) e);
					}
					throw (new IOException(e.toString()));
				}
			}
			// return the map
			return tempMap;
		case 'l':
			// create the list
			List tempList = new ArrayList();
			try {
				// create the key
				Object tempElement = null;
				while ((tempElement = decodeInputStream(bais, nesting + 1)) != null) {
					// add the element
					tempList.add(tempElement);
				}
				bais.mark(Integer.MAX_VALUE);
				tempByte = bais.read();
				bais.reset();
				if (nesting > 0 && tempByte == -1) {
					throw (new IOException("BDecoder: invalid input data, 'e' missing from end of list"));
				}
			} catch (Throwable e) {
				if (!recovery_mode) {
					if (e instanceof IOException) {
						throw ((IOException) e);
					}
					throw (new IOException(e.toString()));
				}
			}
			// return the list
			return tempList;

		case 'e':
		case -1:
			return null;
		case 'i':
			return new Long(getNumberFromStream(bais, 'e'));
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			// move back one
			bais.reset();
			// get the string
			return getByteArrayFromStream(bais);
		default: {
			int rem_len = bais.available();
			if (rem_len > 256) {
				rem_len = 256;
			}
			byte[] rem_data = new byte[rem_len];
			bais.read(rem_data);
			throw (new IOException("BDecoder: unknown command '" + tempByte + ", remainder = " + new String(rem_data, DEFAULT_CHARSET)));
		}
		}
	}

	private long getNumberFromStream(InputStream bais, char parseChar) throws IOException {
		StringBuffer sb = new StringBuffer(3);

		int tempByte = bais.read();
		while ((tempByte != parseChar) && (tempByte >= 0)) {
			sb.append((char) tempByte);
			tempByte = bais.read();
		}

		// are we at the end of the stream?
		if (tempByte < 0) {
			return -1;
		}

		return Long.parseLong(sb.toString());
	}

	// This one causes lots of "Query Information" calls to the filesystem
	private long getNumberFromStreamOld(InputStream bais, char parseChar) throws IOException {
		int length = 0;
		// place a mark
		bais.mark(Integer.MAX_VALUE);
		int tempByte = bais.read();
		while ((tempByte != parseChar) && (tempByte >= 0)) {
			tempByte = bais.read();
			length++;
		}

		// are we at the end of the stream?
		if (tempByte < 0) {
			return -1;
		}

		// reset the mark
		bais.reset();
		// get the length
		byte[] tempArray = new byte[length];
		int count = 0;
		int len = 0;

		// get the string
		while (count != length && (len = bais.read(tempArray, count, length - count)) > 0) {
			count += len;
		}

		// jump ahead in the stream to compensate for the :
		bais.skip(1);
		// return the value
		CharBuffer cb = DEFAULT_CHARSET.decode(ByteBuffer.wrap(tempArray));
		String str_value = new String(cb.array(), 0, cb.limit());

		return Long.parseLong(str_value);
	}

	private byte[] getByteArrayFromStream(InputStream bais) throws IOException {
		int length = (int) getNumberFromStream(bais, ':');
		if (length < 0) {
			return null;
		}
		// note that torrent hashes can be big (consider a 55GB file with 2MB
		// pieces
		// this generates a pieces hash of 1/2 meg

		if (length > 8 * 1024 * 1024) {
			throw new IOException("Byte array length too large (" + length + ")");
		}

		byte[] tempArray = new byte[length];
		int count = 0;
		int len = 0;
		// get the string
		while (count != length && (len = bais.read(tempArray, count, length - count)) > 0) {
			count += len;
		}
		if (count != tempArray.length) {
			throw (new IOException("BDecoder::getByteArrayFromStream: truncated"));
		}
		return tempArray;
	}

	public void setRecoveryMode(boolean r) {
		recovery_mode = r;
	}

	private void print(PrintWriter writer, Object obj) {
		print(writer, obj, "", false);
	}

	private void print(PrintWriter writer, Object obj, String indent, boolean skip_indent) {
		String use_indent = skip_indent ? "" : indent;
		if (obj instanceof Long) {
			writer.println(use_indent + obj);
		} else if (obj instanceof byte[]) {
			byte[] b = (byte[]) obj;
			if (b.length == 20) {
				writer.println(use_indent + " { " + b + " }");
			} else if (b.length < 64) {
				writer.println(new String(b, DEFAULT_CHARSET));
			} else {
				writer.println("[byte array length " + b.length);
			}

		} else if (obj instanceof String) {
			writer.println(use_indent + obj);
		} else if (obj instanceof List) {
			List l = (List) obj;
			writer.println(use_indent + "[");

			for (int i = 0; i < l.size(); i++) {
				writer.print(indent + "  (" + i + ") ");
				print(writer, l.get(i), indent + "    ", true);
			}
			writer.println(indent + "]");

		} else {
			Map m = (Map) obj;
			Iterator it = m.keySet().iterator();

			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.length() > 256) {
					writer.print(indent + key.substring(0, 256) + "... = ");
				} else {
					writer.print(indent + key + " = ");
				}
				print(writer, m.get(key), indent + "  ", true);
			}
		}
	}

	private static void print(File f, File output) {
		try {
			BDecoder decoder = new BDecoder();
			decoder.setRecoveryMode(false);
			PrintWriter pw = new PrintWriter(new FileWriter(output));
			decoder.print(pw, decoder.decodeStream(new BufferedInputStream(new FileInputStream(f))));
			pw.flush();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void down() {
		print(new File("sdcard/d.torrent"), new File("sdcard/d.txt"));
	}
}