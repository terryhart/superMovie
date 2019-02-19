package com.bftv.myapplication.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {
	TextView title;
	private WebViewClient client = new WebViewClient() {

		@Override
		public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
			super.onPageStarted(webView, s, bitmap);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);

		}

		/**
		 * 防止加载网页时调起系统浏览器
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return true;
		}

		@Override
		public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {

			   return super.shouldInterceptRequest(webView,webResourceRequest);

		}

        @Override
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
			Log.e("ddddddd",s);
        }
    };




	private WebChromeClient chromeClient = new WebChromeClient(){
		@Override
		public void onReceivedTitle(WebView webView, String s) {
			super.onReceivedTitle(webView, "测试地址");
			Log.e("kdkkddkkd",s);
		}

		@Override
		public void onProgressChanged(WebView webView, int i) {
			super.onProgressChanged(webView, i);
		}

	};
	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		this.setWebViewClient(client);
		 this.setWebChromeClient(chromeClient);
		// WebStorage webStorage = WebStorage.getInstance();
		initWebViewSettings();
		this.getView().setClickable(true);
	}
	class MyJavaScriptInterface {
		@JavascriptInterface
		@SuppressWarnings("unused")
		public void processHTML(String html) {
			// process the html as needed by the app
			Log.i("htmllll", "processHTML: ===" + html);
		}
	}
	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		// webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		boolean ret = super.drawChild(canvas, child, drawingTime);
		canvas.save();
		Paint paint = new Paint();
		paint.setColor(0x7fff0000);
		paint.setTextSize(24.f);
		paint.setAntiAlias(true);
		//canvas.drawText(getUrl(), 10, 200, paint);
		canvas.restore();
		return ret;
	}

	public X5WebView(Context arg0) {
		super(arg0);
		setBackgroundColor(85621);
	}

	private OnResourceReady l;
	public void setOnResourceReady(OnResourceReady onResourceReady) {
		this.l= onResourceReady;
	}
}
