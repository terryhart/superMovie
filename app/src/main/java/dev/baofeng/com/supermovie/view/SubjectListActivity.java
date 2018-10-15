package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/10/15.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.huangyong.downloadlib.model.Params;
import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.SujectAdapter;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.presenter.GetSujectPresenter;
import dev.baofeng.com.supermovie.presenter.iview.ISubjectView;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/10/15
 * @changeRecord [修改记录] <br/>
 * 2018/10/15 ：created
 */
public class SubjectListActivity extends AppCompatActivity implements ISubjectView, BasePullLayout.OnPullCallBackListener {

    @BindView(R.id.rv_subcat_list)
    RecyclerView rvSubcatList;
    @BindView(R.id.subTitle)
    TextView subTitle;
    @BindView(R.id.refreshLoadMore)
    SimplePullLayout refreshLoadMore;
    private GetSujectPresenter presenter;
    private int index;
    private SubjectInfo info;
    private SujectAdapter adapter;
    private String titleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_list_layout);
        ButterKnife.bind(this);
        titleType = getIntent().getStringExtra(Params.TASK_TITLE_KEY);
        subTitle.setText(titleType);
        initData(titleType);
    }

    private void initData(String titleType) {
        presenter = new GetSujectPresenter(this, this);
        index = 1;
        presenter.getSubject(index, 18, titleType);

        refreshLoadMore.setOnPullListener(this);
    }

    @Override
    public void loadData(SubjectInfo info) {
        this.info = info;
        if (info.getData().size()==0){
            Toast.makeText(this, "数据尚未收录，请耐心等待", Toast.LENGTH_SHORT).show();
        }
        adapter = new SujectAdapter(this, info);
        rvSubcatList.setLayoutManager(new GridLayoutManager(this, 3));
        rvSubcatList.setAdapter(adapter);
    }

    @Override
    public void loadData(SubjectTitleInfo info) {
    }

    @Override
    public void loadError(String msg) {
    }

    @Override
    public void loadMore(SubjectInfo result) {
        info.getData().addAll(result.getData());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMore(SubjectTitleInfo result) {
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getMoreData(1, 18,titleType);
                if (refreshLoadMore!=null){
                    refreshLoadMore.finishPull("加载完成",true);
                }
            }
        },1000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getMoreData(++index,18,titleType);
                refreshLoadMore.finishPull("加载完成",true);
            }
        },1000);
    }
}
