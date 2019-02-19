package dev.baofeng.com.supermovie.view.online.detail;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.google.gson.Gson;

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
import dev.baofeng.com.supermovie.view.GlideRoundTransform;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.HeadDescriptionDialog;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static dev.baofeng.com.supermovie.utils.ColorHelper.colorBurn;

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

    private void initThemeColor() {
        Glide.with(this).load(posterUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                getColor(resource);
            }
        });
        Glide.with(this).load(posterUrl).transform(new CenterCrop(this), new GlideRoundTransform(this, 2)).into(toolbarIcon);


        scrollContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY >= 300) {
                    if (!toolbarIcon.isShown()) {
                        toolbarIcon.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(OnlineDetailPageActivity.this, R.anim.anim_in);
                        toolbarIcon.setAnimation(animation);
                    }

                } else {

                    if (toolbarIcon.isShown()) {
                        Animation animation = AnimationUtils.loadAnimation(OnlineDetailPageActivity.this, R.anim.anim_out);
                        toolbarIcon.setAnimation(animation);
                        toolbarIcon.setVisibility(View.GONE);
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
                Palette.Swatch vibrant = palette.getLightMutedSwatch();
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

        Glide.with(this).load(posterUrl).bitmapTransform(new RoundedCornersTransformation(this, 12, 0, RoundedCornersTransformation.CornerType.ALL)).crossFade(300).into(lineDetailPoster);

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

        OnlinePlayM3u8Adapter adapter2 = new OnlinePlayM3u8Adapter(playUrlBean, posterUrl, title);
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

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
