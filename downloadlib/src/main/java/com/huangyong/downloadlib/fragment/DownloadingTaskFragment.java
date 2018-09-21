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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.adapter.DownTaskAdapter;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.view.DeleteDialog;
import com.xunlei.downloadlib.XLTaskHelper;

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
public class DownloadingTaskFragment extends Fragment implements DownTaskAdapter.OnItemClickListener {


    private RecyclerView downing;
    private List<DowningTaskInfo> infos =new ArrayList<>();
    private DownTaskAdapter adapter;

    private static DownloadingTaskFragment fragment;

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
        getContext().registerReceiver(taskReceiver,intentFilter);
    }

    private void initData() {

        //下载中列表
        List<DowningTaskInfo> taskInfos = TaskDao.getInstance(getContext()).queryAll();
        if (taskInfos.size()>0){
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
                List<DowningTaskInfo> taskInfos = TaskDao.getInstance(getContext()).queryAll();
                if (taskInfos.size()>0){
                    Log.e("downtaskinit","本地数据库有数据"+taskInfos.get(0).getReceiveSize());
                    infos.clear();
                    infos.addAll(taskInfos);
                    adapter.notifyDataSetChanged();
                }else {
                    infos.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public void clicked(DowningTaskInfo info) {

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
                    Toast.makeText(getContext(), "已删除同时删除本地文件", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    infos.clear();
                }
            }
        });
        dialog.findViewById(R.id.deleteTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据库记录
                TaskDao dao = TaskDao.getInstance(getContext());
                dao.delete(taskInfo.getId());

                //删除列表记录
                Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
                if (adapter!=null){
                    adapter.deleteItem(taskInfo.getId());
                    dialog.dismiss();
                    infos.clear();
                }
                //删除迅雷任务
               Log.e("taskpath", taskInfo.getLocalPath());
//                XLTaskHelper.instance().deleteTask(Long.parseLong(taskInfo.getTaskId()),);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(taskReceiver);
    }
}
