package com.huangyong.playerlib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * creator huangyong
 * createTime 2018/11/18 下午4:40
 * path com.huangyong.playerlib
 * description:
 */
public class SpeedDialog extends Dialog implements View.OnClickListener {


    private Button check1;
    private Button check2;
    private Button check3;
    private Button check4;
    private Button check5;

    public SpeedDialog(@NonNull Context context) {
        super(context, R.style.Dialogs_Fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        lp.x=20;
        lp.y=120;
        getWindow().setAttributes(lp);
        setContentView(R.layout.speed_list);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);

        check1.setOnClickListener(this);
        check2.setOnClickListener(this);
        check3.setOnClickListener(this);
        check4.setOnClickListener(this);
         check5.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.check1) {
            setSelectedIndex(0);
        } else if (i == R.id.check2) {
            setSelectedIndex(1);
        } else if (i == R.id.check3) {
            setSelectedIndex(2);
        } else if (i == R.id.check4) {
            setSelectedIndex(3);
        } else if (i == R.id.check5) {
            setSelectedIndex(4);
        }
    }

    public void setSelectedIndex(int index){
        if (listener!=null){
            listener.onChecked(index);
        }
        switch (index){
            case 0:
                check1.setTextColor(getContext().getResources().getColor(R.color.text_selected));
                check2.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check3.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check4.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check5.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                break;
            case 1:
                check1.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check2.setTextColor(getContext().getResources().getColor(R.color.text_selected));
                check3.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check4.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check5.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                break;
            case 2:
                check1.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check2.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check3.setTextColor(getContext().getResources().getColor(R.color.text_selected));
                check4.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check5.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                break;
            case 3:
                check1.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check2.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check3.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check4.setTextColor(getContext().getResources().getColor(R.color.text_selected));
                check5.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                break;
            case 4:
                check1.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check2.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check3.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check4.setTextColor(getContext().getResources().getColor(R.color.text_un_selected));
                check5.setTextColor(getContext().getResources().getColor(R.color.text_selected));
                break;
                default:
                    break;
        }
    }

    private PlayerActivity.OncheckListener listener;
    public void setOnCheckListener(PlayerActivity.OncheckListener listener) {
        this.listener = listener;
    }
}
