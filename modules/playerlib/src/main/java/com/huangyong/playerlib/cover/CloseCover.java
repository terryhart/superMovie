package com.huangyong.playerlib.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.huangyong.playerlib.R;
import com.kk.taurus.playerbase.receiver.BaseCover;

/**
 * creator huangyong
 * createTime 2019/3/11 下午11:34
 * path com.huangyong.playerlib.cover
 * description:
 */
public class CloseCover extends BaseCover {

    ImageView mCloseIcon;

    int EVENT_CODE_REQUEST_CLOSE = -101;


    public CloseCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        mCloseIcon =  getView().findViewById( R.id.iv_close);
        mCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyReceiverEvent(EVENT_CODE_REQUEST_CLOSE, null);
            }
        });
    }


    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_close_cover, null);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getCoverLevel() {
        return levelMedium(10);
    }
}
