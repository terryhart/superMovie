package com.bftv.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amnix.adblockwebview.ui.AdBlocksWebViewActivity;
import com.bftv.myapplication.ParseWebUrlHelper;
import com.bftv.myapplication.PlayActivity;
import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.util.DensityUtil;
import com.bftv.myapplication.webview.X5WebView;

import org.jsoup.Jsoup;

/**
 * Created by Helloworld on 2018/7/20.
 */

public class WebCrossActivity extends AppCompatActivity {

    private X5WebView web;
    private Intent intent;
    private Button geturl;
    private ParseWebUrlHelper parseWebUrlHelper;

    private String[] headUrl = {
            "http://yun.baiyug.cn/vip/index.php?url=",//默认线路1
            "http://jiexi.071811.cc/jx2.php?url=",//
            "http://api.baiyug.cn/vip/?url=",//推荐线路2
      "http://jx.598110.com/duo/index.php?url=",//万能线路2
      "http://jiexi.071811.cc/jx2.php?url=",//万能线路3
      "http://jqaaa.com/jq3/?url=&url=",//万能线路4
      "http://api.91exp.com/svip/?url=",//万能线路5
      "https://jiexi.071811.cc/jx2.php?url=",//万能线路6
      "http://www.82190555.com/index/qqvod.php?url=",//腾讯视频
      "http://api.pucms.com/?url=",//爱奇艺1
      "http://api.baiyug.cn/vip/index.php?url=",//爱奇艺2
      "https://api.flvsp.com/?url=",//爱奇艺3
      "http://api.xfsub.com/index.php?url="//旋风TV
    };
    private String[] urlName = {
      "默认线路1",
      "推荐线路2",
      "万能线路1",
      "万能线路2",
      "万能线路3",
      "万能线路4",
      "万能线路5",
      "万能线路6",
      "万能线路7",
      "万能线路8",
      "万能线路9",
      "万能线路10",
      "万能线路11"

    };
    private PopSpinnerView listView;
    private TextView urltv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_cross_layout);

        web = findViewById(R.id.web);
        listView = findViewById(R.id.psv_list);
        urltv = findViewById(R.id.urltv);
        geturl = findViewById(R.id.getUrl);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        web.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient(){

        });
        web.loadUrl(getIntent().getStringExtra(KeyParam.BASEURL));
        intent = new Intent(WebCrossActivity.this,PlayActivity.class);

        listView.init(headUrl.length, DensityUtil.dip2px(this, 180), new PopSpinnerView.NameFilter() {
            @Override
            public String filter(int position) {
                return urlName[position];
            }
        });
        geturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectIndex = listView.getSelectIndex();
                if (selectIndex == -1) {
                    Toast.makeText(WebCrossActivity.this, "请选择线路", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*Intent intent = new Intent(WebCrossActivity.this, LoadHtmlWebview.class);
                String url = web.getUrl();
                intent.putExtra(KeyParam.PLAYURL,headUrl[listView.getSelectIndex()]+url.replace("m.",""));
                startActivity(intent);
                Log.e("获取地址2",url.replace("m.",""));*/
                String url = web.getUrl();
                AdBlocksWebViewActivity.startWebView(WebCrossActivity.this,headUrl[listView.getSelectIndex()]+url.replace("m.",""),getResources().getColor(R.color.colorPrimary));
            }
        });
        web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {  //表示按返回键
                        web.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        listView.setSelectIndex(0);
        listView.setContent("默认线路1(切换)");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()){
            web.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
