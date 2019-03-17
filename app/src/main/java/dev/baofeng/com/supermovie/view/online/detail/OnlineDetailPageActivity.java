package dev.baofeng.com.supermovie.view.online.detail;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.google.gson.Gson;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;
import com.youngfeng.snake.annotations.EnableDragToClose;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.OnlineCategoryAdapter;
import dev.baofeng.com.supermovie.adapter.OnlinePlayM3u8Adapter;
import dev.baofeng.com.supermovie.adapter.OnlineXunleiAdapter;
import dev.baofeng.com.supermovie.domain.DescBean;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.presenter.GetRandomRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IRandom;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.widget.GlideRoundTransform;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static dev.baofeng.com.supermovie.utils.ColorHelper.colorBurn;

@EnableDragToClose()
public class OnlineDetailPageActivity extends AppCompatActivity implements IRandom {


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
    @BindView(R.id.weburl_title)
    TextView weburlTitle;
    @BindView(R.id.rec_title)
    TextView recTitle;
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.titleview)
    RelativeLayout titleview;
    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.scroll_content)
    NestedScrollView scrollContent;
    @BindView(R.id.toolbarIcon)
    ImageView toolbarIcon;
    @BindView(R.id.desc_content)
    LinearLayout descContent;
    @BindView(R.id.mv_title)
    TextView mvTitle;


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
    private OnlineCategoryAdapter recAdapter;
    private int isMovie;
    private GetRandomRecpresenter randomRecpresenter;
    private int blurColor;
    private String mvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_detail_page);
        ButterKnife.bind(this);
        root.setBackgroundColor(Color.rgb(110, 110, 100));
        toolbar.setBackgroundColor(Color.rgb(110, 110, 100));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cat_topappbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shares:

                ShareEntity testBean = new ShareEntity(title, "看电影，更方便");
                testBean.setContent("热门电影，美剧，海量资源每日更新");
                testBean.setImgUrl(posterUrl);
                testBean.setDrawableId(R.mipmap.icon_share);

                try {
                    if (isMovie==GlobalMsg.MOVIE){
                        testBean.setUrl("https://hiliving.github.io/olineMvShare.html?id="+ mvId);
                    }else {
                        testBean.setUrl("https://hiliving.github.io/olineSrShare.html?id=" + mvId);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ShareUtil.showShareDialog(OnlineDetailPageActivity.this, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.favorate:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initThemeColor() {

        Glide.with(this).asBitmap().load(posterUrl).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                getColor(resource);
            }
        });

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new GlideRoundTransform(this,4));
        //加入圆角变换
        Glide.with(this)
                .load(posterUrl)
                .apply(requestOptions)
                .into(toolbarIcon);

        scrollContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY >= 300) {
                    if (!toolbarIcon.isShown()) {
                        toolbarIcon.setVisibility(View.VISIBLE);
                        appBar.setElevation(8);
                        Animation animation = AnimationUtils.loadAnimation(OnlineDetailPageActivity.this, R.anim.anim_in);
                        toolbarIcon.setAnimation(animation);
                    }

                } else {

                    if (toolbarIcon.isShown()) {
                        Animation animation = AnimationUtils.loadAnimation(OnlineDetailPageActivity.this, R.anim.anim_out);
                        toolbarIcon.setAnimation(animation);
                        toolbarIcon.setVisibility(View.GONE);
                        appBar.setElevation(0);
                    }

                }

            }
        });

    }


    public void getColor(Bitmap bitmap) {
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getMutedSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                if (root != null) {
                    if (vibrant != null) {
                        ValueAnimator colorAnim2 = ValueAnimator.ofArgb(Color.rgb(110, 110, 100), colorBurn(vibrant.getRgb()));
                        colorAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                root.setBackgroundColor((Integer) animation.getAnimatedValue());
                                toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
                            }
                        });
                        colorAnim2.setDuration(300);
                        colorAnim2.setRepeatMode(ValueAnimator.RESTART);
                        colorAnim2.start();

                        if (Build.VERSION.SDK_INT >= 21) {
                            Window window = getWindow();
                            window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                            window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                        }
                    }
                }

            }
        });
    }

    private void initData() {
        randomRecpresenter = new GetRandomRecpresenter(this, this);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        posterUrl = intent.getStringExtra(GlobalMsg.KEY_POST_IMG);
        mvId = intent.getStringExtra(GlobalMsg.KEY_MV_ID);
        playUrl = intent.getStringExtra(GlobalMsg.KEY_PLAY_URL);
        playTitle = intent.getStringExtra(GlobalMsg.KEY_PLAY_TITLE);
        downItemTitle = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE);
        mvType = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TYPE);
        isMovie = intent.getIntExtra(GlobalMsg.KEY_IS_MOVIE, 1);
        downItemList = downItemTitle.split(",");
        downUrl = intent.getStringExtra(GlobalMsg.KEY_DOWN_URL);
        title = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        movDescription = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DETAIL);
        blurColor = intent.getIntExtra(GlobalMsg.KEY_BLUR_COLOR, Color.parseColor("#4b3029"));
        toolbarTitle.setText(title);

        mvTitle.setText(title);

        if (isMovie == GlobalMsg.MOVIE) {
            randomRecpresenter.getMovieRecommend(mvType);
        } else {
            randomRecpresenter.getSeriRecommend(mvType);
        }

        Glide.with(this).load(posterUrl).into(lineDetailPoster);

        initDescData();
        initPlayerData();
        initThemeColor();
    }

    /**
     * 播放，提供浏览器打开功能，以支持腾讯王卡观看
     */
    private void initPlayerData() {

        playUrlBean = gson.fromJson(downUrl, PlayUrlBean.class);
        ArrayList<String> playM3u8List = new ArrayList<>();
        ArrayList<String> playXunleiUrlList = new ArrayList<>();


        for (int i = 0; i < playUrlBean.getNormal().size(); i++) {
            if (playUrlBean.getNormal().size() == 1) {
                playXunleiUrlList.add("web");
            } else {
                playXunleiUrlList.add((i + 1) + "");
            }
        }
        for (int i = 0; i < playUrlBean.getM3u8().size(); i++) {
            if (playUrlBean.getM3u8().size() == 1) {
                playM3u8List.add("播放");
            } else {
                if (playUrlBean.getM3u8().size() > 10) {
                    playM3u8List.add((i + 1) + "");
                    if (i == 9) {
                        break;
                    }
                }
            }

        }

        OnlineXunleiAdapter adapter = new OnlineXunleiAdapter(posterUrl, playUrlBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        playList.setLayoutManager(linearLayoutManager);
        playList.setAdapter(adapter);

        OnlinePlayM3u8Adapter adapter2 = new OnlinePlayM3u8Adapter(this,playUrlBean, posterUrl, title);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        playList2.setLayoutManager(linearLayoutManager2);
        playList2.setAdapter(adapter2);

        if (playM3u8List.size() == 0) {
            playList2.setVisibility(View.GONE);
            m3u8Title.setVisibility(View.GONE);
        }
        if (playXunleiUrlList.size()==0){
            playList.setVisibility(View.GONE);
            weburlTitle.setVisibility(View.GONE);
        }
    }

    private void initDescData() {
        gson = new Gson();
        DescBean descBean = gson.fromJson(movDescription, DescBean.class);
        lineDesc.setContent(descBean.getDesc().trim());

        StringBuilder builder = new StringBuilder();
        //海报右边的短简介
        if (descBean.getHeader() != null)
            for (int i = 0; i < descBean.getHeader().size(); i++) {
                builder.append(descBean.getHeader().get(i)).append("\n");
            }
        headDesc.setText(builder.toString());


        headDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(builder.toString());
            }
        });
        if (TextUtils.isEmpty(descBean.getDesc())) {
            descContent.setVisibility(View.GONE);
        }
    }

    private void showBottomSheetDialog(String string) {
        // Set up BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.head_content_layout, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView headContent = view.findViewById(R.id.head_content);
        headContent.setText(string);
        bottomSheetDialog.show();
    }
    @Override
    public void loadRandomData(OnlinePlayInfo info) {
        recAdapter = new OnlineCategoryAdapter(OnlineDetailPageActivity.this, info, mvType, isMovie);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recList.setLayoutManager(linearLayoutManager);
        recList.setAdapter(recAdapter);
    }

    @Override
    public void loadRError(String msg) {

    }

}
