package dev.baofeng.com.supermovie.view.online;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.flowlayoutlibrary.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.OnlineCategoryAdapter;
import dev.baofeng.com.supermovie.adapter.OnlineSearchAdapter;
import dev.baofeng.com.supermovie.adapter.SearchAdapter;
import dev.baofeng.com.supermovie.db.data.OnlineSearchHistory;
import dev.baofeng.com.supermovie.db.data.SearchHistory;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.presenter.OnlineSearchPresenter;
import dev.baofeng.com.supermovie.presenter.SearchPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IOnlineSearch;
import dev.baofeng.com.supermovie.presenter.iview.Isearch;

/**
 * Created by huangyong on 2018/1/26.
 */

public class OnlineSearchActivity extends AppCompatActivity implements IOnlineSearch {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.search_now)
    Button searchNow;
    @BindView(R.id.clear_et)
    Button clearEt;
    @BindView(R.id.ll_search_layout)
    RelativeLayout llSearchLayout;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.tv_history)
    FlowLayout flKeyword;
    @BindView(R.id.searchrv)
    RecyclerView searchrv;
    @BindView(R.id.list_title)
    TextView listTitle;
    @BindView(R.id.clear_history)
    TextView clearHistory;
    private OnlineSearchPresenter presenter;
    private String keyword;
    private OnlineSearchAdapter adapter;
    private List historyList;
    private ArrayList<OnlineSearchHistory> searchHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        presenter = new OnlineSearchPresenter(this, this);
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
                if (TextUtils.isEmpty(keyword)) {
                    flKeyword.setVisibility(View.VISIBLE);
                    listTitle.setVisibility(View.VISIBLE);
                    clearHistory.setVisibility(View.VISIBLE);
                    if (adapter != null) {
                        adapter.clear();
                    }
                }
            }
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(keyword);
                return true;
            }
            return false;
        });

        clearHistory.setOnClickListener(v -> {
            clearEtFocus();
            Snackbar.make(root, "清除所有搜索记录？", Snackbar.LENGTH_LONG).setAction("确定", v1 -> {
                presenter.clearSearchHistory();
                initSearchHistory();
            }).show();

        });
        searchNow.setOnClickListener(v -> doSearch(keyword));
        clearEt.setOnClickListener(v -> etSearch.setText(""));
        initSearchHistory();
    }

    private void clearEtFocus() {
        if (etSearch.isFocusable()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }


    private void initSearchHistory() {
        searchHistory = presenter.getSearchHistory();
        historyList = new ArrayList();
        for (OnlineSearchHistory itemList : searchHistory) {
            historyList.add(itemList.searchKeyWords);
        }
        // 最后调用setViews方法
        flKeyword.setViews(historyList, content -> {
            doSearch(content);
            etSearch.setText(content);
        });
    }

    private void doSearch(String content) {
        if (!TextUtils.isEmpty(content)) {
            presenter.searchOnline(content);
        } else {
            Toast.makeText(OnlineSearchActivity.this, R.string.search_none_tips, Toast.LENGTH_SHORT).show();
            return;
        }
        refreshSearchHistory(content);
    }

    /**
     * 校验数据库，更新搜索历史，添加或者不添加
     *
     * @param keyword
     */
    private void refreshSearchHistory(String keyword) {
        if (presenter.keywordsExist(keyword)) {
            return;
        } else {

            presenter.addKeyWordsTodb(keyword);
            initSearchHistory();
        }
    }


    @Override
    public void loadData(OnlinePlayInfo info) {
        adapter = new OnlineSearchAdapter(this, info);
        searchrv.setLayoutManager(new GridLayoutManager(this, 3));
        searchrv.setAdapter(adapter);
        flKeyword.setVisibility(View.GONE);
        listTitle.setVisibility(View.GONE);
        clearHistory.setVisibility(View.GONE);
    }

    @Override
    public void loadFail() {
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        flKeyword.setVisibility(View.VISIBLE);
        listTitle.setVisibility(View.VISIBLE);
        clearHistory.setVisibility(View.VISIBLE);
        Toast.makeText(this, "未找到相关内容", Toast.LENGTH_SHORT).show();
    }
}
