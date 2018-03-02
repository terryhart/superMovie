package dev.baofeng.com.supermovie.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HistoryAdapter;
import dev.baofeng.com.supermovie.adapter.TaskAdapter;
import dev.baofeng.com.supermovie.domain.TaskHistory;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.view.GlobalMsg;

/**
 * Created by huangyong on 2018/2/28.
 */

public class TaskRightFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv_task_rigth_list)
    RecyclerView rvTaskRigthList;
    private static TaskRightFragment fragment;
    private LocalBroadcastManager localBroadcastManager;
    private List<TaskHistory> info;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_right_list, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        info = DataSupport.findAll(TaskHistory.class);
        if (info.size()>0){
            adapter = new HistoryAdapter(getContext(), (ArrayList<TaskHistory>) info);
            rvTaskRigthList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTaskRigthList.setAdapter(adapter);
        }else {
            return;
        }
        //注册广播
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalMsg.ACTION);
        localBroadcastManager.registerReceiver(receiver, filter);

    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GlobalMsg.REFRESH)) {
                getActivity().runOnUiThread(() -> {
                    List<TaskHistory> all = DataSupport.findAll(TaskHistory.class);
                    addDataandRefresh(all);
                });
            }
        }
    };

    private void addDataandRefresh(List<TaskHistory> all) {
        info.clear();
        if (all.size() > 0) {
            for (int i = 0; i < all.size(); i++) {
                info.add(all.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public static TaskRightFragment getInstance(){
        if (fragment==null){
            fragment = new TaskRightFragment();
        }
        return fragment;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
