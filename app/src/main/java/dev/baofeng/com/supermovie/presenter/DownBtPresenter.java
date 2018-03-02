package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dev.baofeng.com.supermovie.http.UrlConfig;
import dev.baofeng.com.supermovie.presenter.iview.IBtView;

/**
 * BT种子下载，bt天堂是通过post请求获取种子，不得已就用原始的httpurlconnection了
 * Created by huangyong on 2018/2/8.
 */

public class DownBtPresenter extends BasePresenter<IBtView> {

    public DownBtPresenter(Context context, IBtView iview) {
        super(context, iview);
    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getFile(String id,String action,String uhash,String path){

        URL url = null;
        try {
            url = new URL(UrlConfig.DOWNLOADBASEURL+"download2.php");
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            SSLSocketFactory oldSocketFactory = trustAllHosts(httpURLConnection);
            HostnameVerifier oldHostnameVerifier = httpURLConnection.getHostnameVerifier();
            httpURLConnection.setHostnameVerifier(DO_NOT_VERIFY);
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("accept","*/*");//接收文件类型为所有
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write("id="+id+"&action="+action+"&uhash="+uhash);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            if (httpURLConnection.getResponseCode() == 200) {
                Log.d("dddddaaaaaa", "请求成功");
            }
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String name = System.currentTimeMillis()+"";
            File file = new File(Environment.getExternalStorageDirectory()+File.separator,name+".torrent");
            OutputStream outputStream = new FileOutputStream(file);

            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                outputStream.write(arr,0,len);
                bos.flush();
                outputStream.flush();

            }
            Log.d("dddddaaaaaa", "写入成功");
            iview.onDownSuccess(file.getPath());
            bos.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 覆盖java默认的证书验证
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }};

    /**
     * 设置不验证主机
     */
    private static final HostnameVerifier DO_NOT_VERIFY = (String hostname, SSLSession session)->true;
   /* private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };*/

    /**
     * 信任所有
     * @param connection
     * @return
     */
    private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldFactory;
    }
}
