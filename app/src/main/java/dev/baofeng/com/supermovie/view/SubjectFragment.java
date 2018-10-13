package dev.baofeng.com.supermovie.view;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.SujectAdapter;
import dev.baofeng.com.supermovie.presenter.CenterPresenter;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SubjectFragment extends Fragment implements View.OnClickListener {

    private static SubjectFragment subjectFragment;
    @BindView(R.id.rv_suject_list)
    RecyclerView rvSujectList;
    Unbinder unbinder;
    private CenterPresenter presenter;

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
        //初始化数据
        initData();
    }

    /**
     * 以数据库的为准
     */
    private void initData() {
        SujectAdapter adapter = new SujectAdapter(getContext(),null);
        rvSujectList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSujectList.setAdapter(adapter);
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


    public static String getVersionName(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
