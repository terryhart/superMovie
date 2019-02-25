package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/29.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huangyong.downloadlib.db.FavorDao;
import com.huangyong.downloadlib.domain.FavorInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DoubanAdapter;
import dev.baofeng.com.supermovie.adapter.SearchAdapter;
import dev.baofeng.com.supermovie.domain.DoubanTop250;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.presenter.GetDoubanPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IDBTop250;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/29
 * @changeRecord [修改记录] <br/>
 * 2018/9/29 ：created
 */
public class DoubanActivity extends AppCompatActivity implements IDBTop250 {


    @BindView(R.id.rv_favor_list)
    RecyclerView rvFavorList;
    private DoubanAdapter adapter;
    private GetDoubanPresenter presenter;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.douban_list_layout);
        ButterKnife.bind(this);
    }

    private void initFavordata() {
        presenter = new GetDoubanPresenter(this, this);
        index = 0;
        presenter.getTop250(index * 18, 18);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFavordata();
    }

    @Override
    public void loadData(DoubanTop250 data) {
        rvFavorList.setLayoutManager(new LinearLayoutManager(DoubanActivity.this));
        adapter = new DoubanAdapter(DoubanActivity.this, data);
        rvFavorList.setAdapter(adapter);
    }

    @Override
    public void loadError(String s) {

    }

    @Override
    public void loadMore(DoubanTop250 data) {

    }
}
