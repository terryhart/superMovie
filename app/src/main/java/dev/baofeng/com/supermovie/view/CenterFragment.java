package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.presenter.DownBtPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IBtView;
import dev.baofeng.com.supermovie.utils.BDecoder;

/**
 * Created by huangyong on 2018/1/26.
 */

public class CenterFragment extends Fragment implements View.OnClickListener, IBtView {
    Unbinder unbinder;
    private static CenterFragment homeFragment;
    @BindView(R.id.tv_downing)
    TextView tvDowning;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    private DownBtPresenter presenter;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                long taskId = (long) msg.obj;
                XLTaskInfo taskInfo = XLTaskHelper.instance(MyApp.appInstance()).getTaskInfo(taskId);
                TorrentInfo torrentInfo = XLTaskHelper.instance(MyApp.appInstance()).getTorrentInfo(Environment.getExternalStorageDirectory() + File.separator + "d.torrent");
               /* tvstatu.setText(
                        "fileSize:" + convertFileSize(taskInfo.mFileSize)
                                + "\n" + " downSize:" + convertFileSize(taskInfo.mDownloadSize)
                                + "\n" + " speed:" + convertFileSize(taskInfo.mDownloadSpeed)
                                + "\n" + "/s dcdnSoeed:" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed)
                                + "\n" + "/s filePath:" + "/sdcard/" + torrentInfo.mSubFileInfo
                );*/
                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.center_frag_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static CenterFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new CenterFragment();
        } else {
            return homeFragment;
        }
        return homeFragment;
    }

    private void initView() {
        presenter = new DownBtPresenter(getContext(), this);
        //初始化数据
        initData();
    }

    /**
     * 以数据库的为准
     */
    private void initData() {
        List<TaskInfo> all = DataSupport.findAll(TaskInfo.class);
        tvDowning.setOnClickListener(v -> {
            toggle();
        });
        tvSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AboutUsActivity.class);
            startActivity(intent);
        });
        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDecoder.down();
            }
        });
    }

    private void toggle() {
        if (listener!=null){
            listener.toggle();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        /*new Thread(()-> {
//            presenter.getFile();
        }).start();*/
        long taskId = 0;
        try {
            taskId = XLTaskHelper.instance(MyApp.appInstance()).addTorrentTask(Environment.getExternalStorageDirectory() + File.separator + "d.torrent", "/sdcard/", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendMessage(handler.obtainMessage(0, taskId));
    }

    @Override
    public void onDownSuccess(String path) {

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private OnDownPageListener listener;
    public void setOnDownPageListener(OnDownPageListener onDownPageListener) {
        this.listener = onDownPageListener;
    }

}
