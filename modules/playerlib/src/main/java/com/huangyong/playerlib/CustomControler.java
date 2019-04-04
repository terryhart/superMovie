package com.huangyong.playerlib;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videocontroller.BatteryReceiver;
import com.dueeeke.videocontroller.MarqueeTextView;
import com.dueeeke.videoplayer.controller.GestureVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;
import com.github.ybq.android.spinkit.SpinKitView;
import com.huangyong.playerlib.adapter.PlayListAdapter;
import com.huangyong.playerlib.model.M3u8Bean;

import java.util.List;

import per.goweii.anylayer.AnimHelper;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.LayerManager;

/**
 * creator huangyong
 * createTime 2018/12/6 下午2:14
 * path com.huangyong.playerlib
 * description:
 */
public class CustomControler extends GestureVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static int CurrentIndex = 0;
    private Context activity;
    protected TextView mTotalTime, mCurrTime;
    protected Button mFullScreenButton;
    protected RelativeLayout mBottomContainer;
    protected LinearLayout mTopContainer;
    protected SeekBar mVideoProgress;
    protected ImageView mBackButton;
    protected ImageView mLockButton;
    protected MarqueeTextView mTitle;
    private boolean mIsLive;
    private boolean mIsDragging;

    private ProgressBar mBottomProgress;
    private ImageView mPlayButton;
    private ImageView mStartPlayButton;
    private SpinKitView mLoadingProgress;
    private ImageView mThumb;
    private LinearLayout mCompleteContainer;
    private TextView mSysTime;//系统当前时间
    private ImageView mBatteryLevel;//电量
    private Animation mShowAnim = AnimationUtils.loadAnimation(getContext(), R.anim.dkplayer_anim_alpha_in);
    private Animation mHideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.dkplayer_anim_alpha_out);
    private BatteryReceiver mBatteryReceiver;
    protected ImageView mRefreshButton;
    private SpeedDialog speedDialog;
    private ImageView pic2pic;
    private ImageView airPlay;
    private String loadingTips = "";
    private TextView choseList;
    private boolean showChoseBtn = false;
    private List<M3u8Bean> mPlayList;
    private AnyLayer anyLayer;

    public CustomControler(@NonNull Context context) {
        this(context, null);
        this.activity = context;
    }

    public CustomControler(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomControler(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_conroller;
    }

    @Override
    protected void initView() {
        super.initView();
        mDefaultTimeout = 6000;
        mFullScreenButton = mControllerView.findViewById(R.id.fullscreen);
        mFullScreenButton.setOnClickListener(this);
        mBottomContainer = mControllerView.findViewById(R.id.bottom_container);
        mTopContainer = mControllerView.findViewById(R.id.top_container);
        mVideoProgress = mControllerView.findViewById(R.id.seekBar);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mTotalTime = mControllerView.findViewById(R.id.total_time);
        mCurrTime = mControllerView.findViewById(R.id.curr_time);
        mBackButton = mControllerView.findViewById(R.id.back);
        choseList = mControllerView.findViewById(R.id.chose_list);
        mBackButton.setOnClickListener(this);
        mLockButton = mControllerView.findViewById(R.id.lock);
        mLockButton.setOnClickListener(this);
        mThumb = mControllerView.findViewById(R.id.thumb);
        mThumb.setOnClickListener(this);
        mPlayButton = mControllerView.findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        mStartPlayButton = mControllerView.findViewById(R.id.start_play);
        mLoadingProgress = mControllerView.findViewById(R.id.play_loading);
        mBottomProgress = mControllerView.findViewById(R.id.bottom_progress);
        ImageView rePlayButton = mControllerView.findViewById(R.id.iv_replay);
        rePlayButton.setOnClickListener(this);
        mCompleteContainer = mControllerView.findViewById(R.id.complete_container);
        mCompleteContainer.setOnClickListener(this);
        mTitle = mControllerView.findViewById(R.id.title);
        mSysTime = mControllerView.findViewById(R.id.sys_time);
        mBatteryLevel = mControllerView.findViewById(R.id.iv_battery);


        choseList.setOnClickListener(this);
        pic2pic = mControllerView.findViewById(R.id.pic2pic);
        pic2pic.setOnClickListener(this);
        airPlay = mControllerView.findViewById(R.id.airplay);
        airPlay.setOnClickListener(this);
        mBatteryReceiver = new BatteryReceiver(mBatteryLevel);
        mRefreshButton = mControllerView.findViewById(R.id.iv_refresh);
        mRefreshButton.setOnClickListener(this);
        speedDialog = new SpeedDialog(getContext());

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mBatteryReceiver);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            onBackPressed();
        }
        if (i == R.id.fullscreen) {
            toggleSpeed();
        } else if (i == R.id.lock) {
            doLockUnlock();
        } else if (i == R.id.iv_play || i == R.id.thumb) {
            doPauseResume();
        } else if (i == R.id.iv_replay) {
            mMediaPlayer.retry();
        } else if (i == R.id.iv_refresh) {
            mMediaPlayer.refresh();
        } else if (i == R.id.pic2pic) {
            if (changeListener != null) {
                changeListener.onPic2Pic();
            }
        } else if (i == R.id.airplay) {
            if (changeListener != null) {
                changeListener.onAirPlay();
            }
        } else if (i == R.id.chose_list) {
            showList();
        }
    }

    /**
     * 显示选集对话框
     */
    private void showList() {
        if (anyLayer==null){
            anyLayer = AnyLayer.with(activity)
                    .contentView(R.layout.play_list_layout)
                    .gravity(Gravity.RIGHT)
                    .contentAnim(new LayerManager.IAnim() {
                        @Override
                        public Animator inAnim(View content) {
                            return AnimHelper.createRightInAnim(content);
                        }

                        @Override
                        public Animator outAnim(View content) {
                            return AnimHelper.createRightOutAnim(content);
                        }
                    }).onClick(R.id.ic_list_close, new LayerManager.OnLayerClickListener() {
                        @Override
                        public void onClick(AnyLayer anyLayer, View v) {
                            anyLayer.dismiss();
                        }
                    });
        }
        anyLayer.show();
        RecyclerView playList = anyLayer.getView(R.id.play_list);
        PlayListAdapter adapter = new PlayListAdapter(mPlayList,activity,clickedListener);
        playList.setLayoutManager(new GridLayoutManager(activity,6));
        playList.setAdapter(adapter);
    }

    public void showTitle() {
        mTitle.setVisibility(View.VISIBLE);
    }

    private void toggleSpeed() {
        if (speedDialog != null && !speedDialog.isShowing()) {
            speedDialog.show();

        }
    }

    @Override
    public void setPlayerState(int playerState) {
        switch (playerState) {
            case IjkVideoView.PLAYER_NORMAL:
                L.e("PLAYER_NORMAL");
                if (mIsLocked) return;
                setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mIsGestureEnabled = false;
                mFullScreenButton.setSelected(false);
                mBackButton.setVisibility(View.GONE);
                mLockButton.setVisibility(View.GONE);
                mTitle.setVisibility(View.INVISIBLE);
                mSysTime.setVisibility(View.GONE);
                mBatteryLevel.setVisibility(View.GONE);
                mTopContainer.setVisibility(View.GONE);
                break;
            case IjkVideoView.PLAYER_FULL_SCREEN:
                L.e("PLAYER_FULL_SCREEN");
                if (mIsLocked) return;
                mIsGestureEnabled = true;
                mFullScreenButton.setSelected(true);
                mBackButton.setVisibility(View.VISIBLE);
                mTitle.setVisibility(View.VISIBLE);
                mSysTime.setVisibility(View.VISIBLE);
                mBatteryLevel.setVisibility(View.VISIBLE);
                if (mShowing) {
                    mLockButton.setVisibility(View.VISIBLE);
                    mTopContainer.setVisibility(View.VISIBLE);
                } else {
                    mLockButton.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                hide();
                mIsLocked = false;
                mLockButton.setSelected(false);
                mMediaPlayer.setLock(false);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mVideoProgress.setProgress(0);
                mVideoProgress.setSecondaryProgress(0);
                mCompleteContainer.setVisibility(View.GONE);
                mBottomProgress.setVisibility(View.GONE);
                mLoadingProgress.setVisibility(View.GONE);
                mStartPlayButton.setVisibility(View.VISIBLE);
                mThumb.setVisibility(View.VISIBLE);
                break;
            case IjkVideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                post(mShowProgress);
                mPlayButton.setSelected(true);
                mLoadingProgress.setVisibility(View.GONE);
                mCompleteContainer.setVisibility(View.GONE);
                mThumb.setVisibility(View.GONE);
                mStartPlayButton.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                mPlayButton.setSelected(false);
                mStartPlayButton.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                mCompleteContainer.setVisibility(View.GONE);
                mStartPlayButton.setVisibility(View.GONE);
                mLoadingProgress.setVisibility(View.VISIBLE);
//                mThumb.setVisibility(View.VISIBLE);
                break;
            case IjkVideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                if (!mIsLive) mBottomProgress.setVisibility(View.VISIBLE);
//                mLoadingProgress.setVisibility(GONE);
                mStartPlayButton.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                mStartPlayButton.setVisibility(View.GONE);
                mLoadingProgress.setVisibility(View.GONE);
                mThumb.setVisibility(View.GONE);
                mBottomProgress.setVisibility(View.GONE);
                mTopContainer.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mStartPlayButton.setVisibility(View.GONE);
                mLoadingProgress.setVisibility(View.VISIBLE);
                mThumb.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_BUFFERED:
                mLoadingProgress.setVisibility(View.GONE);
                mStartPlayButton.setVisibility(View.GONE);
                mThumb.setVisibility(View.GONE);
                L.e("STATE_BUFFERED");
                break;
            case IjkVideoView.STATE_PLAYBACK_COMPLETED:
                L.e("STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(mShowProgress);
                mStartPlayButton.setVisibility(View.GONE);
                mThumb.setVisibility(View.VISIBLE);
                mCompleteContainer.setVisibility(View.VISIBLE);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mIsLocked = false;
                mMediaPlayer.setLock(false);
                break;
        }
    }

    protected void doLockUnlock() {
        if (mIsLocked) {
            mIsLocked = false;
            mShowing = false;
            mIsGestureEnabled = true;
            show();
            mLockButton.setSelected(false);
            Toast.makeText(getContext(), R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show();
        } else {
            hide();
            mIsLocked = true;
            mIsGestureEnabled = false;
            mLockButton.setSelected(true);
            Toast.makeText(getContext(), R.string.dkplayer_locked, Toast.LENGTH_SHORT).show();
        }
        mMediaPlayer.setLock(mIsLocked);
    }

    /**
     * 设置是否为直播视频
     */
    public void setLive() {
        mIsLive = true;
        mBottomProgress.setVisibility(View.GONE);
        mVideoProgress.setVisibility(View.INVISIBLE);
        mTotalTime.setVisibility(View.INVISIBLE);
        mCurrTime.setVisibility(View.INVISIBLE);
        mRefreshButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        removeCallbacks(mShowProgress);
        removeCallbacks(mFadeOut);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        long duration = mMediaPlayer.getDuration();
        long newPosition = (duration * seekBar.getProgress()) / mVideoProgress.getMax();
        mMediaPlayer.seekTo((int) newPosition);
        mIsDragging = false;
        post(mShowProgress);
        show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }

        long duration = mMediaPlayer.getDuration();
        long newPosition = (duration * progress) / mVideoProgress.getMax();
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime((int) newPosition));
    }

    @Override
    public void hide() {
        if (mShowing) {
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(View.GONE);
                if (!mIsLocked) {
                    hideAllViews();
                }
            } else {
                mBottomContainer.setVisibility(View.GONE);
                mBottomContainer.startAnimation(mHideAnim);
            }
            if (!mIsLive && !mIsLocked) {
                mBottomProgress.setVisibility(View.VISIBLE);
                mBottomProgress.startAnimation(mShowAnim);
            }
            mShowing = false;

        }
    }

    private void hideAllViews() {
        mTopContainer.setVisibility(View.GONE);
        mTopContainer.startAnimation(mHideAnim);
        mBottomContainer.setVisibility(View.GONE);
        mBottomContainer.startAnimation(mHideAnim);
        choseList.setVisibility(GONE);
        choseList.startAnimation(mHideAnim);
    }

    private void show(int timeout) {
        if (mSysTime != null)
            mSysTime.setText(getCurrentSystemTime());
        if (!mShowing) {
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(View.VISIBLE);
                if (!mIsLocked) {
                    showAllViews();
                }
            } else {
                mBottomContainer.setVisibility(View.VISIBLE);
                mBottomContainer.startAnimation(mShowAnim);
            }
            if (!mIsLocked && !mIsLive) {
                mBottomProgress.setVisibility(View.GONE);
                mBottomProgress.startAnimation(mHideAnim);
            }
            mShowing = true;
        }
        removeCallbacks(mFadeOut);
        if (timeout != 0) {
            postDelayed(mFadeOut, timeout);
        }
    }

    private void showAllViews() {
        mBottomContainer.setVisibility(View.VISIBLE);
        mBottomContainer.startAnimation(mShowAnim);
        mTopContainer.setVisibility(View.VISIBLE);
        mTopContainer.startAnimation(mShowAnim);
        if (showChoseBtn) {
            choseList.setVisibility(VISIBLE);
            choseList.startAnimation(mShowAnim);
        }

    }

    @Override
    public void show() {
        show(mDefaultTimeout);
    }

    @Override
    protected int setProgress() {
        if (mMediaPlayer == null || mIsDragging) {
            return 0;
        }

        if (mTitle != null && TextUtils.isEmpty(mTitle.getText())) {
            mTitle.setText(mMediaPlayer.getTitle());
        }

        if (mIsLive) return 0;

        int position = (int) mMediaPlayer.getCurrentPosition();
        int duration = (int) mMediaPlayer.getDuration();
        if (mVideoProgress != null) {
            if (duration > 0) {
                mVideoProgress.setEnabled(true);
                int pos = (int) (position * 1.0 / duration * mVideoProgress.getMax());
                mVideoProgress.setProgress(pos);
                mBottomProgress.setProgress(pos);
            } else {
                mVideoProgress.setEnabled(false);
            }
            int percent = mMediaPlayer.getBufferedPercentage();
            if (percent >= 95) { //解决缓冲进度不能100%问题
                mVideoProgress.setSecondaryProgress(mVideoProgress.getMax());
                mBottomProgress.setSecondaryProgress(mBottomProgress.getMax());
            } else {
                mVideoProgress.setSecondaryProgress(percent * 10);
                mBottomProgress.setSecondaryProgress(percent * 10);
            }
        }

        if (mTotalTime != null)
            mTotalTime.setText(stringForTime(duration));
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime(position));

        return position;
    }


    @Override
    protected void slideToChangePosition(float deltaX) {
        if (mIsLive) {
            mNeedSeek = false;
        } else {
            super.slideToChangePosition(deltaX);
        }
    }

    public ImageView getThumb() {
        return mThumb;
    }

    @Override
    public boolean onBackPressed() {
        if (mIsLocked) {
            show();
            Toast.makeText(getContext(), R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return true;
        }


        ((Activity)activity).onBackPressed();
        return true;
    }

    public void setOnCheckListener(PlayerActivity.OncheckListener listener) {
        if (speedDialog != null) {
            speedDialog.setOnCheckListener(listener);
        }
    }

    public void setCheckUpdate(String text) {
        if (mFullScreenButton != null) {
            mFullScreenButton.setText(text);
        }
        if (speedDialog != null && speedDialog.isShowing()) {
            speedDialog.dismiss();
        }
    }


    private OnstateChangeListener changeListener;

    public void setOnstateChangeListener(OnstateChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void setLoadingTips(String title) {
        this.loadingTips = title;
        invalidate();
    }

    public void configPlayList(List<M3u8Bean> m3u8Bean, int currentIndex) {
        if (m3u8Bean.size() > 1) {
            CurrentIndex = currentIndex;
            this.showChoseBtn = true;
            this.mPlayList = m3u8Bean;
            invalidate();
        }
    }


    private OnItemClickedListener clickedListener;
    public void setOnItemClickListener(OnItemClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }

    public interface OnstateChangeListener {
        void onAirPlay();

        void onPic2Pic();

        void onLocalCast();
    }

    public interface OnItemClickedListener{
        void clicked(String position);
    }


}
