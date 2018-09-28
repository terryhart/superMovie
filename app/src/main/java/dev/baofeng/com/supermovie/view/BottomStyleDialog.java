package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/28.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.utils.DownloadUtil;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/28
 * @changeRecord [修改记录] <br/>
 * 2018/9/28 ：created
 */
public class BottomStyleDialog extends Dialog implements View.OnClickListener {

    private TextView updateTitle;
    private TextView content;
    private Button confirm;
    private AppUpdateInfo result;

    public BottomStyleDialog(@NonNull Context context, AppUpdateInfo result) {
        super(context, R.style.Dialog_Fullscreen);
        this.result = result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_dialog);

        updateTitle = findViewById(R.id.update_title);
        content = findViewById(R.id.content);
        confirm = findViewById(R.id.confirm);
        updateTitle.setText("是否升级新版本"+result.getData().getVersion());
        content.setText(result.getData().getUpdateMsg());
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"开始下载", Toast.LENGTH_SHORT).show();

        DownloadUtil.get().download(result.getData().getDownloadUrl(), "app_upgrade", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String savePath) {
                Toast.makeText(getContext(), "下载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {

            }
        });
        dismiss();
    }
}
