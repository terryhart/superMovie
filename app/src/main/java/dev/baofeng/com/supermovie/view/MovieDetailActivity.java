package dev.baofeng.com.supermovie.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.db.FavorDao;
import com.huangyong.downloadlib.domain.FavorInfo;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;
import com.youngfeng.snake.annotations.EnableDragToClose;

import java.util.ArrayList;
import java.util.List;

import byc.imagewatcher.ImageWatcher;
import byc.imagewatcher.ImageWatcherHelper;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DetailAdapter;
import dev.baofeng.com.supermovie.adapter.DownListAdapter;
import dev.baofeng.com.supermovie.domain.DetailInfo;
import dev.baofeng.com.supermovie.utils.ImageUtil;
import dev.baofeng.com.supermovie.utils.ImgUtils;
import dev.baofeng.com.supermovie.utils.PixUtil;
import dev.baofeng.com.supermovie.utils.ToastUtil;
import dev.baofeng.com.supermovie.view.widget.GlideSimpleLoader;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static dev.baofeng.com.supermovie.utils.ColorHelper.colorBurn;

/**
 *  intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
 intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
 intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE,datas.getData().get(position).getDownLoadName());
 intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position).getMvdesc());
 */
@EnableDragToClose()
public class MovieDetailActivity extends AppCompatActivity implements OnItemClickListenr {
    private static final String VIEW_NAME_HEADER_IMAGE = "image";
    private static final String VIEW_NAME_HEADER_TITLE = "title";
    private String title;
    private String downUrl;
    private String posterUrl;
    private String mvdescTx;
    private RecyclerView recyclerView;
    private String posterImagUrl;
    private String imgScreenShot;
    private Toolbar toolbar;
    private AppBarLayout detail_app_bar;
    private TextView titleView;
    private DetailAdapter detailAdapter;
    private String downItemTitle;
    private String[] downItemList;
    private LinearLayoutManager layoutManager;
    private String[] items;
    private String playUrl;
    private String playTitle;
    private ImageView mDetailPoster;
    private TextView imgTitle;
    private CoordinatorLayout root;
    private FloatingActionButton fab;
    private DetailInfo info;
    private String sj;
    private String md5Id = "";
    private ArrayList<DetailInfo> downLoadList;
    private ImageView back;
    private CardView posterbg;
    private ImageWatcherHelper iwHelper;
    private StringBuffer shortDesc;
    private Bitmap posterBitmap;
    private int barColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_movie_detail_layout);
        initData();
        initView();
        initImageWatcher();
        root.setBackgroundColor(Color.rgb(110, 110, 100));
    }

    private void initImageWatcher() {
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(0) // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
                    @Override
                    public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, Uri uri, float animatedValue, int actionTag) {
                        Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
                    }

                    @Override
                    public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
                        if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
                        } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                        }
                    }
                });
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
                testBean.setImgUrl(posterImagUrl);
                testBean.setDrawableId(R.mipmap.icon_share);
                try {
                    String url = "https://hiliving.github.io/?id=" + md5Id;
                    Log.e("testlocadsha",url);
                testBean.setUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
              //  ShareUtil.showShareDialog(MovieDetailActivity.this, testBean, ShareConstant.REQUEST_CODE);


                testSharePicture();

                break;
            case R.id.favorate:
                toggleFavor(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testSharePicture() {

        View view = LayoutInflater.from(this).inflate(R.layout.poster_layout, null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        ImageView posterImg = view.findViewById(R.id.detail_poster);
        TextView posterTitle = view.findViewById(R.id.imgTitle);
        TextView posterDesc =  view.findViewById(R.id.desc_container);
        posterTitle.setText(title);
        posterDesc.setText("简介：\n"+mvdescTx.substring(mvdescTx.indexOf("介")+1).trim());
        Bitmap poster = ImageUtil.getScreen(posterbg);
        posterImg.setImageBitmap(poster);
//        view.setBackgroundColor(barColor);
        // validate view.width and view.height
        ImageUtil.layoutView(view, (int) PixUtil.convertDpToPixel(360,this), getResources().getDisplayMetrics().heightPixels);



        Bitmap screen = ImageUtil.getScreen(view);

        if (screen ==null){
            return;
        }


        ShareEntity testBean = new ShareEntity("","");
        testBean.setShareBigImg(true);
        String filePath = ShareUtil.saveBitmapToSDCard(this, screen);
        testBean.setImgUrl(filePath);
        int channel = ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND | ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE | ShareConstant.SHARE_CHANNEL_SINA_WEIBO | ShareConstant.SHARE_CHANNEL_QQ;
        ShareUtil.showShareDialog(this,channel,testBean, ShareConstant.REQUEST_CODE);
    }


    private void initData() {
        layoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        posterUrl = intent.getStringExtra(GlobalMsg.KEY_POST_IMG);
        playUrl = intent.getStringExtra(GlobalMsg.KEY_PLAY_URL);
        playTitle = intent.getStringExtra(GlobalMsg.KEY_PLAY_TITLE);
        downItemTitle = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE);
        md5Id = intent.getStringExtra(GlobalMsg.KEY_MV_ID);
        downItemList = downItemTitle.split(",");


        shortDesc = new StringBuffer();
        if (posterUrl.contains(",")){
           String[] imgArr =  posterUrl.split(",");
            imgScreenShot = imgArr[1];
        }

        posterImagUrl = posterUrl.split(",")[0];
        downUrl = intent.getStringExtra(GlobalMsg.KEY_DOWN_URL);
        title = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        mvdescTx = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DETAIL);
    }


    private void initView() {
        root = findViewById(R.id.root);
        toolbar = findViewById(R.id.toolbars);
        mDetailPoster = findViewById(R.id.detail_poster);
        imgTitle = findViewById(R.id.imgTitle);
        detail_app_bar = findViewById(R.id.app_bar);
        detail_app_bar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        back = findViewById(R.id.back_icon);
        recyclerView = findViewById(R.id.rv_detail);
        titleView = findViewById(R.id.toolbarTitle);
        posterbg = findViewById(R.id.poster_border);
        titleView.setText(title);
        imgTitle.setText(title);
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);


        Glide.with(this).asBitmap().load(posterImagUrl).into(new SimpleTarget<Bitmap>() {



            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                getColor(resource);
                posterBitmap = resource;
            }
        });

        Glide.with(this)
                .load(posterImagUrl)
                .into(mDetailPoster);

        String[] splitArr = mvdescTx.split("◎");
        StringBuffer buffer = new StringBuffer();
        ArrayList<String> listDesc=new ArrayList<>();
        info = new DetailInfo();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (splitArr.length>5){
            for (int i = 1; i < 5; i++) {
                if (splitArr[i].contains("//") ){
                    continue;
                }
                buffer.append(splitArr[i]);
                listDesc.add(splitArr[i]);
            }

            StringBuffer descBuffer =new StringBuffer();
            for (int i = 1; i <splitArr.length ; i++) {
                if (splitArr[i].contains("//")||splitArr[i].contains("类") ){
                    continue;
                }
                if (splitArr[i].contains("简")){
                    descBuffer.append("\n◎"+splitArr[i]);
                }else {
                    descBuffer.append("◎"+splitArr[i]);
                }
            }
            //详情介绍
            info.setMvDesc(descBuffer.toString());
        }else {
            //详情介绍
            for (int i = 0; i < splitArr.length; i++) {
                shortDesc.append(splitArr[i]);
            }
            info.setMvDesc(shortDesc.toString());

        }
        sj = info.getMvDesc();
        if (sj.contains("◎")) {
            sj = sj.substring(sj.lastIndexOf("◎") + 1);
        }



        String[] downUrl = this.downUrl.split(",");
        ArrayList url = new ArrayList();
        for (int i = 0; i < downUrl.length; i++) {
            url.add(downUrl[i]);
        }

        //截屏
        info.setImgScreenShot(imgScreenShot);
        //下载地址
        info.setDownUrl(url);
        //下载页显示的海报
        info.setImgUrl(posterImagUrl);

        downLoadList = new ArrayList<>();
        downLoadList.add(info);
        detailAdapter = new DetailAdapter(downItemList, downLoadList, this);
        DownListAdapter dialogAdapter = new DownListAdapter(downItemList, downLoadList, this);
        recyclerView.setAdapter(detailAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
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
            info.setMovieDesc(mvdescTx);
            info.setPostImgUrl(posterImagUrl);
            info.setTitle(title);
            info.setTaskUrl(downUrl);
            info.setUrlMd5(md5);
            info.setDownItemTitle(downItemTitle);
            dao.add(info);
            Toast.makeText(this, "已添加收藏", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.ic_favorite_black_24dp);
        }

    }

    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float progress = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
            if (progress >= 0.8) {
                toolbar.setVisibility(View.VISIBLE);
                titleView.setAlpha(progress);
                posterbg.setAlpha(1 - progress);
            } else {
                toolbar.setVisibility(View.VISIBLE);
                titleView.setAlpha(0.0f);
                posterbg.setAlpha(1 - progress);
            }
        }
    }

    @Override
    public void clicked(String url, String imgUrl) {
        TaskLibHelper.addNewTask(url, Params.getPath(),imgUrl,getApplicationContext());

        ToastUtil.showMessage("下载任务已添加");
    }

    @Override
    public void clickedPic(ImageView screenShot, String screenShotImagUrl) {
        SparseArray list = new SparseArray();
        list.append(0, screenShot);
        List<Uri> url = new ArrayList<>();
        url.add(0, Uri.parse(screenShotImagUrl));
        iwHelper.show(screenShot, list, url);
    }

    @Override
    public void onBackPressed() {
        if (!iwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
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
                                // toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
                                detail_app_bar.setBackgroundColor((Integer) animation.getAnimatedValue());
                            }
                        });
                        colorAnim2.setDuration(300);
                        colorAnim2.setRepeatMode(ValueAnimator.RESTART);
                        colorAnim2.start();

                        if (Build.VERSION.SDK_INT >= 21) {
                            Window window = getWindow();
                            window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                            barColor = colorBurn(vibrant.getRgb());
                            window.setNavigationBarColor(barColor);
                        }
                    }
                }

            }
        });
    }


    private void showBottomSheetDialog() {
        // Set up BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.download_sheet_layout, null);
        handleList(view);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();
    }

    private void handleList(View bottomSheetInternal) {

        RecyclerView recyclerView = bottomSheetInternal.findViewById(R.id.down_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    root.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        recyclerView.setLayoutManager(manager);
        DownListAdapter dialogAdapter = new DownListAdapter(downItemList, downLoadList, this);
        recyclerView.setAdapter(dialogAdapter);
    }

}
