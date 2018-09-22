package com.huangyong.downloadlib.utils;

import android.os.Environment;
import android.os.StatFs;

import com.huangyong.downloadlib.model.Params;

import java.io.File;
import java.io.FileInputStream;

public class FileUtils {
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f M" : "%.1f M", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f K" : "%.1f K", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 获取机身内存占用情况，第一个为可用，第二个为总共
     * @return
     */
    public static String[] getSpaceSize(){
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long totalSpace = sdcard_filedir.getTotalSpace();
        String usable = convertFileSize(usableSpace);
        String total = convertFileSize(totalSpace);
        return new String[] {usable,total};
    }

    /**
     * 获取下载目录文件大小
     * @return
     */
    public static String getCacheSize(){
        File sdcard_filedir = new File(Params.DEFAULT_PATH);//得到sdcard的目录作为一个文件对象
        try {
            return convertFileSize(getFileSizes(sdcard_filedir));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

}
