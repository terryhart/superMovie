package com.huangyong.playerlib;

import android.content.Context;

import com.huangyong.playerlib.widget.MyIjkDecoder;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.kk.taurus.playerbase.entity.DecoderPlan;
import com.kk.taurus.playerbase.record.PlayRecordManager;

import zmovie.com.dlan.DlanLib;


/**
 * creator huangyong
 * createTime 2019/3/16 上午7:40
 * path com.huangyong.playerlib
 * description:这个是初始化PlayBase播放库使用的
 */
public class PlayerApplication {

    public static final int PLAN_ID_IJK = 1;

    private static Context context;
    public static void init(Context contexts){
        context = contexts;
        //播放记录的配置
        PlayerConfig.addDecoderPlan(new DecoderPlan(
                PLAN_ID_IJK,
                MyIjkDecoder.class.getName(),
                "ijkplayer"));
        PlayerConfig.setDefaultPlanId(PLAN_ID_IJK);
        PlayerLibrary.init(context);
        //开启播放记录
        PlayerConfig.playRecord(true);
        PlayRecordManager.setRecordConfig(
                new PlayRecordManager.RecordConfig.Builder()
                        .setMaxRecordCount(600)
                        //.setRecordKeyProvider()
                        //.setOnRecordCallBack()
                        .build());


        //投屏功能初始化
        DlanLib.initDlan(contexts);
    }


    public static Context getAppContext(){
        return context;
    }
}
