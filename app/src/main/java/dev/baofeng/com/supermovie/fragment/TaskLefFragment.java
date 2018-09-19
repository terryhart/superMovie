package dev.baofeng.com.supermovie.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.TaskAdapter;
import dev.baofeng.com.supermovie.bt.ThreadUtils;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.utils.SizeUtils;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import io.reactivex.Observable;
import rx.Subscriber;

/**
 * Created by huangyong on 2018/2/28.
 */

public class TaskLefFragment extends Fragment {

    @BindView(R.id.rv_task_left_list)
    RecyclerView rvTaskLeftList;
    Unbinder unbinder;
    private static TaskLefFragment fragment;
    @BindView(R.id.tips)
    TextView tips;
    private TaskAdapter adapter;
    private List<TaskInfo> dataList;
    private LocalBroadcastManager localBroadcastManager;

    private TaskInfo data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_left_list, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {


        //如果数据库没有数据，说明当前下载列表为空，那就不显示
        dataList = DataSupport.findAll(TaskInfo.class);

        if (dataList == null) {
//            dataList = new ArrayList<>();
            tips.setVisibility(View.VISIBLE);
            return;
        }else {
            tips.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < dataList.size(); i++) {
            Log.d("TaskInfolog", dataList.get(i).getIsWaiting() + "");
        }
        adapter = new TaskAdapter(getContext(), dataList);
        rvTaskLeftList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTaskLeftList.setAdapter(adapter);

        //注册广播
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalMsg.ACTION);
        localBroadcastManager.registerReceiver(receiver, filter);
    }

    public static TaskLefFragment getInstance() {
        if (fragment == null) {
            fragment = new TaskLefFragment();
        }
        return fragment;
    }

    @Override
    public void onDestroyView() {
        adapter.saveData();
        unbinder.unbind();
        super.onDestroyView();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GlobalMsg.ACTION)) {
                getActivity().runOnUiThread(() -> {
                    List<TaskInfo> all = DataSupport.findAll(TaskInfo.class);
                    addDataandRefresh(all);
                    Log.d("TaskInfoOOOOO:", "收到广播了，列表应该更新了" + all.size());
                });
                Log.d("TaskInfoOOOOO:", "收到广播了" + intent.getAction());
            } else if (intent.getAction().equals(GlobalMsg.ACTIONWAIT)) {
                getActivity().runOnUiThread(() -> {
                    List<TaskInfo> all = DataSupport.findAll(TaskInfo.class);
                    addWaitRefresh(all);
                    Log.d("TaskInfoOOOOO:", "收到广播了，列表应该更新了" + all.size());
                });
                Log.d("TaskInfoOOOOO:", "收到广播了,开始等待" + intent.getAction());
            } else {
                Log.d("TaskInfoOOOOO:", "收到广播了,开始等待1" + intent.getAction());
            }
        }
    };


    private void refreshProgress(List<TaskInfo> taskId) {
      //  handler.sendMessage(handler.obtainMessage(0, taskId));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(receiver);
        data.save();
        super.onDestroy();
    }

    public void addDataandRefresh(List<TaskInfo> all) {
        dataList.clear();
        if (all.size() > 0) {
            for (int i = 0; i < all.size(); i++) {
                dataList.add(all.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void addWaitRefresh(List<TaskInfo> all) {
        dataList.clear();
        if (all.size() > 0) {
            for (int i = 0; i < all.size(); i++) {
                dataList.add(all.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
