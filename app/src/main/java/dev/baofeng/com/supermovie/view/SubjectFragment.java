package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.SujectTitleAdapter;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.presenter.CenterPresenter;
import dev.baofeng.com.supermovie.presenter.GetSujectPresenter;
import dev.baofeng.com.supermovie.presenter.iview.ISubjectView;
import dev.baofeng.com.supermovie.view.loadmore.LoadMoreWrapper;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SubjectFragment extends Fragment implements View.OnClickListener, ISubjectView{

    private static SubjectFragment subjectFragment;
    @BindView(R.id.rv_suject_list)
    XRecyclerView rvSujectList;
    Unbinder unbinder;
    private GetSujectPresenter getSujectPresenter;
    private int index = 1;
    private SubjectTitleInfo infoList;
    private SujectTitleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suject_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static SubjectFragment getInstance() {
        if (subjectFragment == null) {
            subjectFragment = new SubjectFragment();
        } else {
            return subjectFragment;
        }
        return subjectFragment;
    }

    private void initView() {

    }

    private void initData() {
        getSujectPresenter = new GetSujectPresenter(getContext(), this);
        getSujectPresenter.getSubjectTitle(index, 12);


        rvSujectList.getDefaultFootView().setLoadingHint("正在加载请稍后");
        rvSujectList.getDefaultFootView().setNoMoreHint("已经到底了");
        rvSujectList.setLimitNumberToCallLoadMore(2);
        rvSujectList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvSujectList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvSujectList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rvSujectList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvSujectList.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                getSujectPresenter.getMoreTitleData(++index,18);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化数据
        initData();
    }

    @Override
    public void loadData(SubjectInfo info) {
    }

    @Override
    public void loadData(SubjectTitleInfo info) {
        this.infoList = info;
        if (rvSujectList!=null){
            adapter = new SujectTitleAdapter(getContext(), infoList);
            rvSujectList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvSujectList.setAdapter(adapter);
        }

    }

    @Override
    public void loadError(String msg) {
        rvSujectList.setNoMore(true);
    }

    @Override
    public void loadMore(SubjectInfo result) {
    }

    @Override
    public void loadMore(SubjectTitleInfo result) {
        infoList.getData().addAll(result.getData());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        rvSujectList.loadMoreComplete();
    }
}
