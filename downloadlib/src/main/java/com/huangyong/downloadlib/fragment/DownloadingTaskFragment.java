package com.huangyong.downloadlib.fragment;
/**
 * Created by HuangYong on 2018/9/19.
 */

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.adapter.DownTaskAdapter;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.huangyong.downloadlib.utils.Utils;
import com.huangyong.downloadlib.view.DeleteDialog;
import com.huangyong.playerlib.PlayerActivity;
import com.xunlei.downloadlib.XLTaskHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
public class DownloadingTaskFragment extends Fragment implements DownTaskAdapter.OnItemClickListener, ITask {


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
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downing = view.findViewById(R.id.rv_downing_task);
        downing.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DownTaskAdapter(infos);
        initData();
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.UPDATE_PROGERSS);
        intentFilter.addAction(Params.TASK_COMMPLETE);
        getContext().registerReceiver(taskReceiver,intentFilter);
    }

    private void initData() {
        presenter = new DownLoadPresenter(getContext(),this);
        //下载中列表
        List<DowningTaskInfo> taskInfos = TaskDao.getInstance(getContext()).queryAll();
        if (taskInfos!=null&&taskInfos.size()>0){
            Log.e("downtaskinit","本地数据库有数据"+taskInfos.size());
            this.infos.clear();
            this.infos.addAll(taskInfos);
            adapter.notifyDataSetChanged();
        }else {
            Log.e("downtaskinit","本地数据库为空");
        }
        downing.setAdapter(adapter);
        adapter.setOnItemclickListenr(this);
    }

    public void updateTaskDatas(List<DowningTaskInfo> info, DownLoadMainActivity downLoadMainActivity) {
        if (this.infos!=null){
            this.infos.clear();
            this.infos.addAll(info);
        }
        if (adapter!=null){
            Log.e("zhegadatep","dapter是空的");
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 下载中的任务完成后，会从下载中移除，并添加到已完成的列表，已完成因为没有下载进度，所以不会实时更新，只
     * 在数据变化时更新，所以已完成的列表在这个页面封装并传送
     */
    BroadcastReceiver taskReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Params.UPDATE_PROGERSS)){
                //查询数据库所有数据
                List<DowningTaskInfo> downingTaskInfos = TaskDao.getInstance(getContext()).queryAll();
                if (downingTaskInfos!=null&&downingTaskInfos.size()>0){
                    infos.clear();
                    infos.addAll(downingTaskInfos);
                    adapter.notifyDataSetChanged();
                }
            }
            if (intent.getAction().equals(Params.TASK_COMMPLETE)){
                TaskDao dao = TaskDao.getInstance(getContext());
                List<DowningTaskInfo> taskInfos =dao.queryAll();
                if (taskInfos.size()>0){
                    synchronized (DownloadingTaskFragment.class){
                        infos.clear();
                        infos.addAll(taskInfos);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    synchronized (DownloadingTaskFragment.class){
                        infos.clear();
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        }
    };

    @Override
    public void clicked(DowningTaskInfo info) {

        //已暂停，点击重新开始下载
        if (info.getStatu()==0||info.getStatu()==4&&!TextUtils.isEmpty(info.getTaskId())){
            Toast.makeText(getContext(), "下载已开始", Toast.LENGTH_SHORT).show();

            //由于本身的重新启动下载有点问题，无法重启下载，折衷的策略就是删除任务，重新添加，幸好支持续传
            XLTaskHelper.instance().removeTask(Long.parseLong(info.getTaskId()));
            if (info.getTitle().contains("共")){
                //种子的重启任务
                Log.e("torrentRestart",info.getTaskId()+"正在重启");
                if (presenter!=null){
                    presenter.restartTorrent(info);
                }
            }else {
//                XLTaskHelper.instance().startTask(Long.parseLong(info.getTaskId()));
                TaskLibHelper.reStartTask(info.getTaskUrl(),info.getLocalPath(),info.getPostImgUrl(),getActivity());
            }

        }else {
            //正在下载，点击停止
            Toast.makeText(getContext(), "已暂停下载", Toast.LENGTH_SHORT).show();
            XLTaskHelper.instance().stopTask(Long.parseLong(info.getTaskId()));
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
                Toast.makeText(getContext(), "链接已拷贝", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.deleteTaskAndFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDao dao = TaskDao.getInstance(getContext());
                dao.delete(taskInfo.getId());
                if (adapter!=null){
                    adapter.deleteItem(taskInfo.getId());

                    XLTaskHelper.instance().removeTask(Long.parseLong(taskInfo.getTaskId()));

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
        dialog.findViewById(R.id.deleteTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据库记录
                TaskDao dao = TaskDao.getInstance(getContext());
                dao.delete(taskInfo.getId());

                XLTaskHelper.instance().removeTask(Long.parseLong(taskInfo.getTaskId()));
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
        getContext().unregisterReceiver(taskReceiver);
    }

    @Override
    public void repeatAdd(String s) {

    }
}
