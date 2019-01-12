package dev.baofeng.com.supermovie.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ManifestUtil;


/**
 * creator huangyong
 * createTime 2018/12/26 下午10:40
 * path dev.baofeng.com.supermovie.wxapi
 * description:
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ManifestUtil.getWeixinKey(this), false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Intent intent = new Intent();
        intent.setAction(ShareConstant.ACTION_WEIXIN_CALLBACK);
        intent.putExtra(ShareConstant.EXTRA_WEIXIN_RESULT, resp.errCode);
        sendBroadcast(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}
