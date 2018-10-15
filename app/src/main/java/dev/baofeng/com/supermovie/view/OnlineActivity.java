package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.BTcategoryAdapter;
import dev.baofeng.com.supermovie.adapter.BtDownAdapter;
import dev.baofeng.com.supermovie.adapter.CategoryAdapter;
import dev.baofeng.com.supermovie.adapter.OnlineCategoryAdapter;
import dev.baofeng.com.supermovie.adapter.OnlineDetailAdapter;
import dev.baofeng.com.supermovie.domain.OnlineInfo;
import dev.baofeng.com.supermovie.presenter.GetOnlinePresenter;
import dev.baofeng.com.supermovie.presenter.iview.IOnlineView;

public class OnlineActivity extends AppCompatActivity implements BasePullLayout.OnPullCallBackListener, IOnlineView {

    @BindView(R.id.rvOnline)
    RecyclerView rvOnline;
    @BindView(R.id.onlineRefresh)
    SimplePullLayout onlineRefresh;
    private GetOnlinePresenter presenter;
    private int index;
    private OnlineCategoryAdapter adapter;
    private OnlineInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        onlineRefresh.setOnPullListener(this);
        presenter = new GetOnlinePresenter(this,this);
        index = 1;
        presenter.getOnlineData(index,18);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getOnlineData(1, 18);
                if (onlineRefresh!=null){
                    onlineRefresh.finishPull("加载完成",true);
                }
            }
        },1000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getMoreData(++index,18);
                onlineRefresh.finishPull("加载完成",true);
            }
        },1000);
    }

    @Override
    public void loadData(OnlineInfo info) {
        this.info = info;
        adapter = new OnlineCategoryAdapter(this,info);
        rvOnline.setLayoutManager(new GridLayoutManager(this,3));
        rvOnline.setAdapter(adapter);
    }

    @Override
    public void loadError(String msg) {

    }

    @Override
    public void loadMore(OnlineInfo result) {
        this.info.getData().addAll(result.getData());
        adapter.notifyDataSetChanged();
    }
}
