package dev.baofeng.com.supermovie.view;

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
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;
import com.xunlei.downloadlib.parameter.XLTaskInfo;
import com.yaoxiaowen.download.FileInfo;
import com.yaoxiaowen.download.db.DbHolder;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.bt.FocusGroupBean;
import dev.baofeng.com.supermovie.bt.PinnedHeaderExpandableAdapter;
import dev.baofeng.com.supermovie.bt.PinnedHeaderExpandableListView;
import dev.baofeng.com.supermovie.bt.VeDetailBean;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.presenter.DownBtPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IBtView;

/**
 * Created by huangyong on 2018/1/26.
 */

public class CenterFragment extends Fragment implements View.OnClickListener, IBtView {
    Unbinder unbinder;
    private static CenterFragment homeFragment;
    //保存重点关注组的数据
    private List<FocusGroupBean> focusList;
    private PinnedHeaderExpandableListView explistview;
    private PinnedHeaderExpandableAdapter adapter;
    private int expandFlag;
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
        explistview = (PinnedHeaderExpandableListView) view.findViewById(R.id.exp_list_view);
        unbinder = ButterKnife.bind(this, view);

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
        //展示数据
        showData(focusList);
    }

    private void showData(List<FocusGroupBean> focusList) {
        // 设置头布局
        explistview.setHeaderView(getLayoutInflater().inflate(
                R.layout.group_head, explistview, false));
        // 对适配器进行非空判断
        if (adapter == null) {
            adapter = new PinnedHeaderExpandableAdapter(focusList,
                    getActivity(), explistview);
            explistview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        // 设置滑动的监听
        explistview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 在滑动状态改变的时候 查看是否有保存的已打开的子条目 如果有则关闭
                if (adapter.openLayout != null) {
                    adapter.openLayout.closeLayout();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // 当列表滑动的时候 头布局进行相关变化
                final long flatPos = explistview
                        .getExpandableListPosition(firstVisibleItem);
                int groupPosition = ExpandableListView
                        .getPackedPositionGroup(flatPos);
                int childPosition = ExpandableListView
                        .getPackedPositionChild(flatPos);
                explistview.configureHeaderView(groupPosition, childPosition);

            }

        });
    }

    /**
     * 以数据库的为准
     *
     */
    private void initData() {
/* List<TaskInfo> all = DataSupport.findAll(TaskInfo.class);
        for (int i = 0; i < all.size(); i++) {
            //hbList.add(new VeDetailBean(all.get(i).getName()));
            Log.d("DDDBADADATEA",all.get(i).getName());
        }*/
        focusList = new ArrayList<>();
        //创建河北组的数据
        List<VeDetailBean> hbList = new ArrayList<>();
        hbList.add(new VeDetailBean("变形金刚"));
        hbList.add(new VeDetailBean("变形金刚"));
        hbList.add(new VeDetailBean("变形金刚"));
        focusList.add(new FocusGroupBean("正在下载", hbList));

        //创建北京组的数据
        List<VeDetailBean> bjList = new ArrayList<>();
        bjList.add(new VeDetailBean("变形金刚"));
        bjList.add(new VeDetailBean("变形金刚2"));
        bjList.add(new VeDetailBean("变形金刚3"));
        bjList.add(new VeDetailBean("变形金刚2"));
        bjList.add(new VeDetailBean("变形金刚9"));
        bjList.add(new VeDetailBean("变形金刚7"));
        bjList.add(new VeDetailBean("变形金刚2"));
        bjList.add(new VeDetailBean("变形金刚2"));
        focusList.add(new FocusGroupBean("下载完成", bjList));
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
        initView();
    }
}
