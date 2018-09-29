package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.huangyong.downloadlib.DownLoadMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.CenterPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IAllView;
import dev.baofeng.com.supermovie.utils.BDecoder;

/**
 * Created by huangyong on 2018/1/26.
 */

public class CenterFragment extends Fragment implements View.OnClickListener, IAllView {
    Unbinder unbinder;
    private static CenterFragment homeFragment;
    @BindView(R.id.tv_downing)
    TextView tvList;
    @BindView(R.id.tv_about)
    TextView tvhistory;
    @BindView(R.id.tv_setting)
    TextView tvFavor;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    private CenterPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.center_frag_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
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
        presenter = new CenterPresenter(getContext(), this);
        //初始化数据
        initData();
    }

    /**
     * 以数据库的为准
     */
    private void initData() {
        //任务列表
        tvList.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DownLoadMainActivity.class);
            startActivity(intent);
        });
        //收藏
        tvFavor.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AboutUsActivity.class);
            startActivity(intent);
        });
        //观看记录
        tvhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
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
    }

    private OnDownPageListener listener;
    public void setOnDownPageListener(OnDownPageListener onDownPageListener) {
        this.listener = onDownPageListener;
    }

    @Override
    public void loadSuccess(RecentUpdate movieBean) {

    }

    @Override
    public void loadMore(RecentUpdate movieBean) {

    }
}
