package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.SearchAdapter;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.presenter.SearchPresenter;
import dev.baofeng.com.supermovie.presenter.iview.ISview;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SearchActivity extends AppCompatActivity implements ISview {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.search_now)
    Button searchNow;
    @BindView(R.id.clear_et)
    Button clearEt;
    @BindView(R.id.ll_search_layout)
    RelativeLayout llSearchLayout;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.searchrv)
    RecyclerView searchrv;
    private SearchPresenter presenter;
    private String keyword;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        presenter = new SearchPresenter(this,this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyword = s.toString();
            }
        });
        searchNow.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(keyword)){
                presenter.search(keyword);
            }else {
                Toast.makeText(SearchActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
            }

        });
        clearEt.setOnClickListener(v -> etSearch.setText(""));
    }

    @Override
    public void onResult(MovieInfo info) {
        adapter = new SearchAdapter(this, info);
        searchrv.setLayoutManager(new LinearLayoutManager(this));
        searchrv.setAdapter(adapter);
    }

    @Override
    public void onNoData() {
        if (adapter!=null){
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "未找到相关内容", Toast.LENGTH_SHORT).show();
    }
}
