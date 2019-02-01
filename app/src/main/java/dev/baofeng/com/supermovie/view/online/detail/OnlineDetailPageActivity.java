package dev.baofeng.com.supermovie.view.online.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.OnlinePlayWebAdapter;
import dev.baofeng.com.supermovie.adapter.OnlinePlayM3u8Adapter;
import dev.baofeng.com.supermovie.domain.DescBean;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.view.GlideRoundTransform;
import dev.baofeng.com.supermovie.view.GlobalMsg;

public class OnlineDetailPageActivity extends AppCompatActivity {


    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.line_detail_poster)
    ImageView lineDetailPoster;
    @BindView(R.id.line_desc)
    ExpandableTextView lineDesc;
    @BindView(R.id.play_list)
    RecyclerView playList;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.head_desc)
    TextView headDesc;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.desc_title)
    TextView descTitle;
    @BindView(R.id.m3u8_title)
    TextView m3u8Title;
    @BindView(R.id.play_list2)
    RecyclerView playList2;


    private String posterUrl;
    private String playUrl;
    private String playTitle;
    private String downItemTitle;
    private String mvType;
    private String[] downItemList;
    private String downUrl;
    private String title;
    private String movDescription;
    private Gson gson;
    private PlayUrlBean playUrlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_detail_page);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();


    }

    private void initData() {
        Intent intent = getIntent();
        posterUrl = intent.getStringExtra(GlobalMsg.KEY_POST_IMG);
        playUrl = intent.getStringExtra(GlobalMsg.KEY_PLAY_URL);
        playTitle = intent.getStringExtra(GlobalMsg.KEY_PLAY_TITLE);
        downItemTitle = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE);
        mvType = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TYPE);
        downItemList = downItemTitle.split(",");
        downUrl = intent.getStringExtra(GlobalMsg.KEY_DOWN_URL);
        title = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        movDescription = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DETAIL);
        toolbarTitle.setText(title);

        Glide.with(this).load(posterUrl).transform(new CenterCrop(this), new GlideRoundTransform(this, 4)).into(lineDetailPoster);
        initDescData();
        initPlayerData();
    }

    /**
     * 播放，提供浏览器打开功能，以支持腾讯王卡观看
     */
    private void initPlayerData() {

        playUrlBean = gson.fromJson(downUrl, PlayUrlBean.class);
        ArrayList<String> playM3u8List = new ArrayList<>();
        ArrayList<String> playWebUrlList = new ArrayList<>();

        for (int i = 0; i < playUrlBean.getNormal().size(); i++) {
            if (playUrlBean.getNormal().size() == 1) {
                playWebUrlList.add("在线观看");
            } else {
                playWebUrlList.add("第" + (i + 1) + "集");
            }
        }
        for (int i = 0; i < playUrlBean.getM3u8().size(); i++) {
            if (playUrlBean.getM3u8().size() == 1) {
                playM3u8List.add("在线观看");
            } else {
                if (playUrlBean.getM3u8().size() > 10) {
                    playM3u8List.add("第" + (i + 1) + "集");
                    if (i == 9) {
                        break;
                    }
                }
            }

        }

        OnlinePlayWebAdapter adapter = new OnlinePlayWebAdapter(playUrlBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        playList.setLayoutManager(linearLayoutManager);
        playList.setAdapter(adapter);

        OnlinePlayM3u8Adapter adapter2 = new OnlinePlayM3u8Adapter(playUrlBean, posterUrl, title);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        playList2.setLayoutManager(linearLayoutManager2);
        playList2.setAdapter(adapter2);


    }

    private void initDescData() {
        gson = new Gson();
        final DescBean descBean = gson.fromJson(movDescription, DescBean.class);
        lineDesc.setContent(descBean.getDesc());


        //海报右边的短简介
        final StringBuilder mDescHeader = new StringBuilder();
        if (descBean.getHeader_key().size() > descBean.getHeader_value().size()) {
            for (int i = 0; i < descBean.getHeader_value().size(); i++) {

                if (TextUtils.isEmpty(descBean.getHeader_value().get(i).trim())) {
                    continue;
                }
                mDescHeader.append(descBean.getHeader_key().get(i) + descBean.getHeader_value().get(i) + "\n");
            }
        } else {
            for (int i = 0; i < descBean.getHeader_key().size(); i++) {

                if (TextUtils.isEmpty(descBean.getHeader_value().get(i).trim())) {
                    continue;
                }
                mDescHeader.append(descBean.getHeader_key().get(i) + descBean.getHeader_value().get(i) + "\n");
            }
        }
        headDesc.setText(mDescHeader.toString());
        if (TextUtils.isEmpty(descBean.getDesc())) {
            descTitle.setVisibility(View.GONE);
        }
    }
}
