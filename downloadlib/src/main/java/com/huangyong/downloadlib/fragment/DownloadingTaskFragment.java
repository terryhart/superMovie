package com.huangyong.downloadlib.fragment;
/**
 * Created by HuangYong on 2018/9/19.
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.adapter.DownTaskAdapter;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.huangyong.downloadlib.room.AppDatabaseManager;
import com.huangyong.downloadlib.room.DowningTaskDao;
import com.huangyong.downloadlib.room.data.DowningTaskInfo;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.huangyong.downloadlib.utils.Utils;
import com.huangyong.downloadlib.view.DeleteDialog;
import com.huangyong.playerlib.PlayerActivity;
import com.xunlei.downloadlib.XLTaskHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description 下载中
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/19
 * @changeRecord [修改记录] <br/>
 * 2018/9/19 ：created
 */
public class DownloadingTaskFragment extends Fragment implements DownTaskAdapter.OnItemClickListener {

    private RecyclerView downing;
    private List<DowningTaskInfo> infos =new ArrayList<>();
    private DownTaskAdapter adapter;

    private static DownloadingTaskFragment fragment;
    private DownLoadPresenter presenter;

    public static DownloadingTaskFragment getInstance(){
        if (fragment==null){
            fragment = new DownloadingTaskFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.task_list_ing,null);
        initView(v);
        return v;
    }


    private void initView(View view) {
        downing = view.findViewById(R.id.rv_downing_task);
        downing.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DownTaskAdapter(infos);
        List<DowningTaskInfo> taskInfos = AppDatabaseManager.getInstance(getActivity()).donwingDao().getAll();
        if (taskInfos != null && taskInfos.size() > 0) {
            adapter.setTaskData(taskInfos);
        }
    }
    private void initData() {
        //下载中列表
        downing.setAdapter(adapter);
        adapter.setOnItemclickListenr(this);
    }

    @Override
    public void clicked(final DowningTaskInfo info) {

        //已暂停，点击重新开始下载
        if (info.getStatu()==0||info.getStatu()==4){

            Toast.makeText(getContext(), "下载已开始", Toast.LENGTH_SHORT).show();

            //由于本身的重新启动下载有点问题，无法重启下载，折衷的策略就是删除任务，重新添加，幸好支持续传
            //XLTaskHelper.instance().removeTask(Long.parseLong(info.getTaskId()));
            if (info.getTitle().contains("共")){
                //种子的重启任务
                Log.e("torrentRestart",info.getTaskId()+"正在重启");
                if (presenter!=null){
                    presenter.restartTorrent(info);
                }
            }else {

                if (presenter != null) {
                    presenter.restartNormalTask(info);

                }

            }

        }else {

            //先停止对应任务
            XLTaskHelper.instance().removeTask(Long.parseLong(info.getTaskId()));

            //正在下载，点击停止,停止的同时，要重置任务的id为随机数，避免新添加的任务产生混乱
            DowningTaskDao downingTaskDao = AppDatabaseManager.getInstance(getActivity()).donwingDao();
            List<DowningTaskInfo> taskInfos = downingTaskDao.getAll();
            if (taskInfos != null && taskInfos.size() > 0) {
                for (int i = 0; i < taskInfos.size(); i++) {
                    if (taskInfos.get(i).getUrlMd5().equals(info.getUrlMd5())) {
                        taskInfos.get(i).setTaskId(Utils.generateRandomId());
                        taskInfos.get(i).setStatu(4);
                        downingTaskDao.update(taskInfos.get(i));
                    }
                }
            }

            Toast.makeText(getContext(), "已暂停下载", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void longclicked(final DowningTaskInfo taskInfo) {
        final Dialog dialog = DeleteDialog.getInstance(getContext(), R.layout.delete_alert_layout);
        dialog.show();
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.copyLink).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Utils.copyToClipboard(getContext(),taskInfo.getTaskUrl());
                Toast.makeText(getContext(), "链接已拷贝:\n\n" + taskInfo.getTaskUrl().substring(0, 50) + "......", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.deleteTaskAndFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DowningTaskDao dao = AppDatabaseManager.getInstance(getContext()).donwingDao();
                dao.delete(taskInfo);
                if (adapter!=null){
                    adapter.deleteItem(taskInfo.getId());

                    if (!taskInfo.getTaskId().contains("-")) {
                        XLTaskHelper.instance().removeTask(Long.parseLong(taskInfo.getTaskId()));
                    }


                    File file = new File(taskInfo.getFilePath());
                    if (file.exists()){
                        file.delete();
                    }
                    BroadCastUtils.sendIntentBroadCask(getContext(),new Intent(),Params.UPDATE_MEMERY_SIZE);
                    Toast.makeText(getContext(), "已删除同时删除本地文件", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
        });
        //删除任务，但不删除本地文件
        dialog.findViewById(R.id.deleteTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据库记录
                DowningTaskDao dao = AppDatabaseManager.getInstance(getContext()).donwingDao();
                dao.delete(taskInfo);

                if (!taskInfo.getTaskId().contains("-")) {
                    XLTaskHelper.instance().removeTask(Long.parseLong(taskInfo.getTaskId()));
                }
                //删除列表记录
                Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
                BroadCastUtils.sendIntentBroadCask(getContext(),new Intent(),Params.UPDATE_MEMERY_SIZE);
                if (adapter!=null){
                    adapter.deleteItem(taskInfo.getId());
                    dialog.dismiss();

                }
            }
        });
    }

    @Override
    public void clicktoplay(DowningTaskInfo taskInfo) {
        String proxPlayUlr = XLTaskHelper.instance().getLoclUrl(taskInfo.getLocalPath()+"/"+taskInfo.getTitle());
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        intent.putExtra(Params.PROXY_PALY_URL,proxPlayUlr);
        intent.putExtra(Params.URL_MD5_KEY, MD5Utils.stringToMD5(taskInfo.getLocalPath()+"/"+taskInfo.getTitle()));
        intent.putExtra(Params.POST_IMG_KEY,taskInfo.getPostImgUrl());
        intent.putExtra(Params.TASK_TITLE_KEY,taskInfo.getTitle());
        intent.putExtra(Params.MOVIE_PROGRESS,"0");
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void setPresenter(DownLoadPresenter downLoadPresenter) {
        this.presenter = downLoadPresenter;
    }

    public void FlushData(List<DowningTaskInfo> taskInfo) {
        if (adapter != null) {
            adapter.setTaskData(taskInfo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("kdkkdkdkd", "task destroed");
    }
}
