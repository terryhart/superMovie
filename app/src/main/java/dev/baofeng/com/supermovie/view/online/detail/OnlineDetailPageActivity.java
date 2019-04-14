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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.google.gson.Gson;
import com.huangyong.downloadlib.db.FavorDao;
import com.huangyong.downloadlib.domain.FavorInfo;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;
import com.youngfeng.snake.annotations.EnableDragToClose;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.OnlineCategoryAdapter;
import dev.baofeng.com.supermovie.adapter.OnlinePlayM3u8Adapter;
import dev.baofeng.com.supermovie.adapter.OnlineXunleiAdapter;
import dev.baofeng.com.supermovie.adapter.PieRandomAdapter;
import dev.baofeng.com.supermovie.domain.DescBean;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.presenter.GetRandomRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IRandom;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.widget.GlideRoundTransform;
import dev.baofeng.com.supermovie.view.widget.PileLayout;

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
    RecyclerView m3u8List;
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
    @BindView(R.id.poster_border)
    CardView posterBorder;
    @BindView(R.id.pie_container)
    PileLayout pieContainer;


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
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(downUrl)) {
            MenuItem item = menu.findItem(R.id.favorate);
            FavorDao dao = FavorDao.getInstance(getApplicationContext());
            String md5 = MD5Utils.stringToMD5(downUrl);
            List<FavorInfo> favorInfos = dao.queryForFeilds("urlMd5", md5);
            if (favorInfos != null && favorInfos.size() > 0) {
                item.setIcon(R.drawable.ic_favorite_black_24dp);
            } else {
                item.setIcon(R.drawable.ic_favorite_border_black_24dp);
            }

        }
        return super.onPrepareOptionsMenu(menu);
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
                    if (isMovie == GlobalMsg.MOVIE) {
                        testBean.setUrl("https://hiliving.github.io/olineMvShare.html?id=" + mvId);
                    } else {
                        testBean.setUrl("https://hiliving.github.io/olineSrShare.html?id=" + mvId);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ShareUtil.showShareDialog(OnlineDetailPageActivity.this, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.favorate:
                toggleFavor(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 添加或取消收藏
     * @param item
     */
    private void toggleFavor(MenuItem item) {

        FavorDao dao = FavorDao.getInstance(getApplicationContext());
        String md5 = MD5Utils.stringToMD5(downUrl);
        List<FavorInfo> favorInfos = dao.queryForFeilds("urlMd5", md5);
        if (favorInfos!=null&&favorInfos.size()>0){
            dao.delete(favorInfos.get(0).getId());
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.ic_favorite_border_black_24dp);

        }else {
            FavorInfo info = new FavorInfo();
            //详情
            info.setMovieDesc(movDescription);
            //海报
            info.setPostImgUrl(posterUrl);
            //标题
            info.setTitle(title);
            //下载地址集合
            info.setDownload_url(downUrl);
            //唯一标签
            info.setUrlMd5(md5);
            //下载列表标题（好像没用到）
            info.setDownItemTitle(downItemTitle);
            //资源类型，区分离线还是在线
            info.setContent_type(GlobalMsg.CONTENT_M3U8);
            //资源类型，区分电视剧还是电影
            info.setIs_movie(isMovie+"");
            //影片分类key，根据这个type去请求不同分类的资源
            info.setMovie_type(mvType);
            dao.add(info);
            Toast.makeText(this, "已添加收藏", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.ic_favorite_black_24dp);
        }

    }

    private void initThemeColor() {

        Glide.with(this).asBitmap().load(posterUrl).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                getColor(resource);
            }
        });

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new GlideRoundTransform(this, 4));
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
        OnlineXunleiAdapter adapter = new OnlineXunleiAdapter(posterUrl, playUrlBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        playList.setLayoutManager(linearLayoutManager);
        playList.setAdapter(adapter);

        OnlinePlayM3u8Adapter adapter2 = new OnlinePlayM3u8Adapter(this, playUrlBean, posterUrl, title);
        LinearLayoutManager horizontalManager = new LinearLayoutManager(this);
        horizontalManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        m3u8List.setLayoutManager(horizontalManager);
        m3u8List.setAdapter(adapter2);

        if (playUrlBean.getM3u8() != null && playUrlBean.getM3u8().size() == 0) {
            m3u8List.setVisibility(View.GONE);
            m3u8Title.setVisibility(View.GONE);
        }
        if (playUrlBean.getXunlei() != null && playUrlBean.getXunlei().size() == 0) {
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
        PieRandomAdapter adapter = new PieRandomAdapter(OnlineDetailPageActivity.this, info, isMovie, mvType);
        pieContainer.setAdapter(adapter);
    }

    @Override
    public void loadRError(String msg) {

    }


}
