package com.bftv.myapplication.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.webview.OnResourceReady;
import com.bftv.myapplication.webview.X5WebView;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.MimeTypeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
//        webSetting.setUserAgentString("Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36");
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
