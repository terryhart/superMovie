package com.bftv.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.webview.X5WebView;

/**
 * Created by Helloworld on 2018/7/21.
 */

public class WebviewPlayer extends AppCompatActivity {


    private X5WebView mWebView;
    private WebView root;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_html);
        url = getIntent().getStringExtra(KeyParam.PLAYURL);
        mWebView = findViewById(R.id.root);
        final com.tencent.smtt.sdk.WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
//        webSetting.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        webSetting.setJavaScriptEnabled(true);

        webSetting.setAppCacheEnabled(false);
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        mWebView.setInitialScale(-150);
        mWebView.loadUrl(url);

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mWebView.destroyDrawingCache();
        mWebView.destroy();
        super.onDestroy();
    }



}
