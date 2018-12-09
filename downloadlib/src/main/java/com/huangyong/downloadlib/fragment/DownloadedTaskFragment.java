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

import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.adapter.DownedTaskAdapter;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.huangyong.downloadlib.utils.FileUtils;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.huangyong.downloadlib.utils.Utils;
import com.huangyong.downloadlib.view.DeleteDialog;
import com.huangyong.playerlib.PlayerActivity;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description 已完成
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/19
 * @changeRecord [修改记录] <br/>
 * 2018/9/19 ：created
 */
public class DownloadedTaskFragment extends Fragment implements DownedTaskAdapter.OnPressListener, ITask {

    private List<DoneTaskInfo> taskInfos = new ArrayList<>();
    private RecyclerView downed;
    private DownedTaskAdapter adapter;


    private static DownloadedTaskFragment fragment;
    private DownLoadPresenter presenter;

    public static DownloadedTaskFragment getInstance(){
        if (fragment==null){
            fragment=new DownloadedTaskFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.task_list_ed,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downed = view.findViewById(R.id.rv_downed_task);
        downed.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DownedTaskAdapter(taskInfos);
        adapter.setOnLongPressListener(this);
        downed.setAdapter(adapter);
        presenter = DownLoadPresenter.getInstance(getContext(),this);
        initReceiver();

    }

    public void updateTaskData(List<DoneTaskInfo> infoList) {
        if (taskInfos!=null){
            taskInfos.clear();
            taskInfos.addAll(infoList);
        }
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.TASK_COMMPLETE);
        getContext().registerReceiver(taskReceiver,intentFilter);
    }
    @Override
    public void longPressed(final DoneTaskInfo taskInfo) {
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
                Toast.makeText(getContext(), "链接已拷贝"+taskInfo.getTaskUrl(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.deleteTaskAndFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskedDao dao = TaskedDao.getInstance(getContext());
                dao.delete(taskInfo.getId());
                if (adapter!=null){

                    adapter.deleteItem(taskInfo.getId());

                    File file = new File(taskInfo.getFilePath());
                    if (file.exists()){
                        file.delete();
                    }

                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    BroadCastUtils.sendIntentBroadCask(getContext(),new Intent(),Params.UPDATE_MEMERY_SIZE);
                    dialog.dismiss();
                }
            }
        });
        dialog.findViewById(R.id.deleteTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskedDao dao = TaskedDao.getInstance(getContext());
                dao.delete(taskInfo.getId());
                adapter.deleteItem(taskInfo.getId());
                BroadCastUtils.sendIntentBroadCask(getContext(),new Intent(),Params.UPDATE_MEMERY_SIZE);
                Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
                if (adapter!=null){
                    adapter.deleteItem(taskInfo.getId());
                    dialog.dismiss();
                }

            }
        });
    }

    @Override
    public void clicked(final DoneTaskInfo taskInfo) {
        if (taskInfo.getTitle().endsWith(".torrent")){
            /*final Dialog dialog = BtDownloadDialog.getInstance(getContext(), R.layout.bt_down_load_layout);
            dialog.show();*/
            File file = new File(taskInfo.getLocalPath()+"/"+taskInfo.getTitle());
            if (!file.exists()){
                Toast.makeText(getContext(), "本地文件不存在，可能已被删除", Toast.LENGTH_SHORT).show();
                return;
            }

            final TorrentInfo torrentInfo = XLTaskHelper.instance().getTorrentInfo(taskInfo.getLocalPath()+"/"+taskInfo.getTitle());
            Log.e("torrentinfo",torrentInfo.mFileCount+"");
            CheckBoxDialog.showCheckBoxDialog(getContext(), torrentInfo, taskInfo.getLocalPath() + "/" + taskInfo.getTitle(), new CheckBoxDialog.OnChoseFileListener() {
                @Override
                public void onDownLoadTask(List<Integer> index, List<String> choseName) {
                    if (presenter!=null){
                        String torrentPath = taskInfo.getLocalPath()+"/"+taskInfo.getTitle();
                        try {
                            String savePath = FileUtils.isExistDir(Params.DEFAULT_PATH);
                            presenter.addTorrentTask(taskInfo,torrentPath,savePath,index,torrentInfo,true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else {
            String loacalURL = taskInfo.getLocalPath()+"/"+taskInfo.getTitle();
            Log.e("localpath",loacalURL);
            File file = new File(loacalURL);
            if (!file.exists()){
                Toast.makeText(getContext(), "本地文件不存在，可能已被删除", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getActivity(), PlayerActivity.class);
            intent.putExtra(Params.PROXY_PALY_URL,taskInfo.getFilePath());
            intent.putExtra(Params.URL_MD5_KEY, MD5Utils.stringToMD5(loacalURL));
            intent.putExtra(Params.POST_IMG_KEY,taskInfo.getPostImgUrl());
            intent.putExtra(Params.TASK_TITLE_KEY,taskInfo.getTitle());
            intent.putExtra(Params.MOVIE_PROGRESS,"0");
            startActivity(intent);
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
                // TODO 只有页面可见时才更新列表。但是服务中的数据封装实时进行着。除非任务列表为空。本页只初始化时从数据库获取一次
            }
            if (intent.getAction().equals(Params.TASK_COMMPLETE)){
                TaskedDao dao = TaskedDao.getInstance(getContext());
                List<DoneTaskInfo> doneTaskInfos = dao.queryAll();
                if (doneTaskInfos!=null&&doneTaskInfos.size()>0){
                    taskInfos.clear();
                    taskInfos.addAll(doneTaskInfos);
                }
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(taskReceiver);
    }

    @Override
    public void repeatAdd(String s) {

    }
}
