package dev.baofeng.com.supermovie.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.huangyong.downloadlib.model.Params;

import java.io.File;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.services.DownLoadService;

public class UpdateDialog extends Dialog {

    private AppUpdateInfo info;
    private ProgressBar update_progress;
    private TextView confirm;
    private TextView loading;

    public UpdateDialog(Context context, AppUpdateInfo info) {
        super(context,  R.style.Dialog_Fullscreen);
        this.info = info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);
        setCanceledOnTouchOutside(false);
        TextView title =findViewById(R.id.update_title);
        TextView content =findViewById(R.id.update_content);
        confirm = findViewById(R.id.bt_confirm);
        update_progress = findViewById(R.id.update_progress);
        loading = findViewById(R.id.bt_loading);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.GONE);
                update_progress.setVisibility(View.VISIBLE);
                String downloadUrl = info.getData().getDownloadUrl();
                if (TextUtils.isEmpty(downloadUrl)){
                    return;
                }
                Intent intent = new Intent(getContext(), DownLoadService.class);
                intent.putExtra(Params.DURL,downloadUrl);
                getContext().startService(intent);
                Toast.makeText(getContext(), "正在下载更新，请耐心等待……", Toast.LENGTH_SHORT).show();

            }
        });

        title.setText("发现新版本："+info.getData().getVersion());

        content.setText(info.getData().getUpdateMsg());

    }



    public void setProgress(int extra) {
        update_progress.setProgress(extra);
        loading.setText("正在更新："+extra+"%");
    }
}
