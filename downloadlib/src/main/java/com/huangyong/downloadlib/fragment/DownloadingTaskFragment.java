package com.huangyong.downloadlib.fragment;
/**
 * Created by HuangYong on 2018/9/19.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.adapter.DownTaskAdapter;
import com.huangyong.downloadlib.domain.DowningTaskInfo;

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
public class DownloadingTaskFragment extends Fragment {


    private RecyclerView downing;
    private List<DowningTaskInfo> infos =new ArrayList<>();
    private DownTaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.task_list_ing,null);
        downing = v.findViewById(R.id.rv_downing_task);
        downing.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DownTaskAdapter();
        adapter.setTaskData(infos);
        downing.setAdapter(adapter);
        return v;
    }

    public void updateTaskData(List<DowningTaskInfo> info) {
        if (this.infos!=null){
            this.infos.clear();
            this.infos.addAll(info);
        }
        adapter.notifyDataSetChanged();
    }
}
