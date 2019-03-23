package com.huangyong.playerlib.cover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huangyong.playerlib.R;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.BundlePool;
import com.kk.taurus.playerbase.event.EventKey;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.log.PLog;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.player.OnTimerUpdateListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.receiver.IReceiverGroup;
import com.kk.taurus.playerbase.touch.OnTouchGestureListener;
import com.kk.taurus.playerbase.utils.TimeUtil;


/**
 * Created by Taurus on 2018/4/15.
 */

public class ControllerCover extends BaseCover implements OnTimerUpdateListener, OnTouchGestureListener, View.OnClickListener {

    private static boolean TIME_DELAY_FINISH = false;
    private final int MSG_CODE_DELAY_HIDDEN_CONTROLLER = 101;


    int EVENT_CODE_UPDATE_SEEK = -201;
    String KEY_IS_LANDSCAPE = "isLandscape";

    String KEY_DATA_SOURCE = "data_source";

    String KEY_ERROR_SHOW = "error_show";

    String KEY_COMPLETE_SHOW = "complete_show";
    String KEY_CONTROLLER_TOP_ENABLE = "controller_top_enable";
    String KEY_CONTROLLER_SCREEN_SWITCH_ENABLE = "screen_switch_enable";

    String KEY_TIMER_UPDATE_ENABLE = "timer_update_enable";

    String KEY_NETWORK_RESOURCE = "network_resource";

    private int mBufferPercentage;

    private int mSeekProgress = -1;

    private boolean mTimerUpdateProgressEnable = true;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CODE_DELAY_HIDDEN_CONTROLLER:
                    PLog.d(getTag().toString(), "msg_delay_hidden...");
                    setControllerState(false);
                    break;
            }
        }
    };

    private boolean mGestureEnable = true;

    private String mTimeFormat;

    private boolean mControllerTopEnable;
    private ObjectAnimator mBottomAnimator;
    private ObjectAnimator mTopAnimator;
    private ImageView floatWindowBt;

    public ControllerCover(Context context) {
        super(context);
    }

    View mTopContainer;
    View mBottomContainer;
    ImageView mBackIcon;
    TextView mTopTitle;
    ImageView mStateIcon;
    TextView mCurrTime;
    TextView mTotalTime;
    ImageView mSwitchScreen;
    SeekBar mSeekBar;
    SeekBar mBottomSeekBar;

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        View view = getView();
        mTopContainer = view.findViewById(R.id.cover_player_controller_top_container);
        mBottomContainer = view.findViewById(R.id.cover_player_controller_bottom_container);
        mBackIcon = view.findViewById(R.id.cover_player_controller_image_view_back_icon);
        mTopTitle = view.findViewById(R.id.cover_player_controller_text_view_video_title);
        mStateIcon = view.findViewById(R.id.cover_player_controller_image_view_play_state);
        mCurrTime = view.findViewById(R.id.cover_player_controller_text_view_curr_time);
        mTotalTime = view.findViewById(R.id.cover_player_controller_text_view_total_time);
        mSwitchScreen = view.findViewById(R.id.cover_player_controller_image_view_switch_screen);
        mSeekBar = view.findViewById(R.id.cover_player_controller_seek_bar);
        mBottomSeekBar = view.findViewById(R.id.cover_bottom_seek_bar);
        floatWindowBt = view.findViewById(R.id.small_window);
        floatWindowBt.setOnClickListener(this);
        mBackIcon.setOnClickListener(this);
        mStateIcon.setOnClickListener(this);
        mSwitchScreen.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        getGroupValue().registerOnGroupValueUpdateListener(mOnGroupValueUpdateListener);

    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        DataSource dataSource = getGroupValue().get(KEY_DATA_SOURCE);
        setTitle(dataSource);

        boolean topEnable = getGroupValue().getBoolean(KEY_CONTROLLER_TOP_ENABLE, true);
        mControllerTopEnable = topEnable;
        if (!topEnable) {
            setTopContainerState(false);
        }

        boolean screenSwitchEnable = getGroupValue().getBoolean(KEY_CONTROLLER_SCREEN_SWITCH_ENABLE, true);
        setScreenSwitchEnable(screenSwitchEnable);
    }

    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
        mTopContainer.setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.GONE);
        removeDelayHiddenMessage();
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();

        cancelTopAnimation();
        cancelBottomAnimation();

        getGroupValue().unregisterOnGroupValueUpdateListener(mOnGroupValueUpdateListener);
        removeDelayHiddenMessage();
        mHandler.removeCallbacks(mSeekEventRunnable);


    }

    int EVENT_CODE_REQUEST_BACK = -100;
    int EVENT_CODE_REQUEST_CLOSE = -101;

    int EVENT_CODE_REQUEST_TOGGLE_SCREEN = -104;

    int EVENT_CODE_ERROR_SHOW = -111;

    int EVENT_CODE_TO_FLOAT_MODE = 221;

    private IReceiverGroup.OnGroupValueUpdateListener mOnGroupValueUpdateListener =
            new IReceiverGroup.OnGroupValueUpdateListener() {
                @Override
                public String[] filterKeys() {
                    return new String[]{
                            KEY_COMPLETE_SHOW,
                            KEY_TIMER_UPDATE_ENABLE,
                            KEY_DATA_SOURCE,
                            KEY_IS_LANDSCAPE,
                            KEY_CONTROLLER_TOP_ENABLE};
                }

                @Override
                public void onValueUpdate(String key, Object value) {
                    if (key.equals(KEY_COMPLETE_SHOW)) {
                        boolean show = (boolean) value;
                        if (show) {
                            setControllerState(false);
                        }
                        setGestureEnable(!show);
                    } else if (key.equals(KEY_CONTROLLER_TOP_ENABLE)) {
                        mControllerTopEnable = (boolean) value;
                        if (!mControllerTopEnable) {
                            setTopContainerState(false);
                        }
                    } else if (key.equals(KEY_IS_LANDSCAPE)) {
                        setSwitchScreenIcon((Boolean) value);
                    } else if (key.equals(KEY_TIMER_UPDATE_ENABLE)) {
                        mTimerUpdateProgressEnable = (boolean) value;
                    } else if (key.equals(KEY_DATA_SOURCE)) {
                        DataSource dataSource = (DataSource) value;
                        setTitle(dataSource);
                    }
                }
            };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser)
                        updateUI(progress, seekBar.getMax());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    sendSeekEvent(seekBar.getProgress());
                }
            };

    private void sendSeekEvent(int progress) {
        mTimerUpdateProgressEnable = false;
        mSeekProgress = progress;
        mHandler.removeCallbacks(mSeekEventRunnable);
        mHandler.postDelayed(mSeekEventRunnable, 300);
    }

    private Runnable mSeekEventRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSeekProgress < 0)
                return;
            Bundle bundle = BundlePool.obtain();
            bundle.putInt(EventKey.INT_DATA, mSeekProgress);
            requestSeek(bundle);
        }
    };

    private void setTitle(DataSource dataSource) {
        if (dataSource != null) {
            String title = dataSource.getTitle();
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
                return;
            }
            String data = dataSource.getData();
            if (!TextUtils.isEmpty(data)) {
                setTitle(data);
            }
        }
    }

    private void setTitle(String text) {
        mTopTitle.setText(text);
    }

    private void setSwitchScreenIcon(boolean isFullScreen) {
        mSwitchScreen.setImageResource(isFullScreen ? R.mipmap.icon_exit_full_screen : R.mipmap.icon_full_screen);
    }

    private void setScreenSwitchEnable(boolean screenSwitchEnable) {
        mSwitchScreen.setVisibility(screenSwitchEnable ? View.VISIBLE : View.GONE);
    }

    private void setBottomSeekBarState(boolean state) {
        mBottomSeekBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void setGestureEnable(boolean gestureEnable) {
        this.mGestureEnable = gestureEnable;
    }

    private void cancelTopAnimation() {
        if (mTopAnimator != null) {
            mTopAnimator.cancel();
            mTopAnimator.removeAllListeners();
            mTopAnimator.removeAllUpdateListeners();
        }
    }

    private void setTopContainerState(final boolean state) {
        if (mControllerTopEnable) {
            mTopContainer.clearAnimation();
            cancelTopAnimation();
            mTopAnimator = ObjectAnimator.ofFloat(mTopContainer,
                    "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
            mTopAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (state) {
                        mTopContainer.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!state) {
                        mTopContainer.setVisibility(View.GONE);
                    }
                }
            });
            mTopAnimator.start();
        } else {
            mTopContainer.setVisibility(View.GONE);
        }
    }

    private void cancelBottomAnimation() {
        if (mBottomAnimator != null) {
            mBottomAnimator.cancel();
            mBottomAnimator.removeAllListeners();
            mBottomAnimator.removeAllUpdateListeners();
        }
    }

    private void setBottomContainerState(final boolean state) {
        mBottomContainer.clearAnimation();
        cancelBottomAnimation();
        mBottomAnimator = ObjectAnimator.ofFloat(mBottomContainer,
                "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
        mBottomAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (state) {
                    mBottomContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!state) {
                    mBottomContainer.setVisibility(View.GONE);
                }
            }
        });
        mBottomAnimator.start();
        setBottomSeekBarState(!state);
    }

    private void setControllerState(boolean state) {
        if (state) {
            sendDelayHiddenMessage();
        } else {
            removeDelayHiddenMessage();
        }
        setTopContainerState(state);
        setBottomContainerState(state);
    }

    private boolean isControllerShow() {
        return mBottomContainer.getVisibility() == View.VISIBLE;
    }

    private void toggleController() {
        if (isControllerShow()) {
            setControllerState(false);
        } else {
            setControllerState(true);
        }
    }

    private void sendDelayHiddenMessage() {
        removeDelayHiddenMessage();
        mHandler.sendEmptyMessageDelayed(MSG_CODE_DELAY_HIDDEN_CONTROLLER, 5000);
    }

    private void removeDelayHiddenMessage() {
        mHandler.removeMessages(MSG_CODE_DELAY_HIDDEN_CONTROLLER);
    }

    private void setCurrTime(int curr) {
        mCurrTime.setText(TimeUtil.getTime(mTimeFormat, curr));
    }

    private void setTotalTime(int duration) {
        mTotalTime.setText(TimeUtil.getTime(mTimeFormat, duration));
    }

    private void setSeekProgress(int curr, int duration) {
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(curr);
        float secondProgress = mBufferPercentage * 1.0f / 100 * duration;
        setSecondProgress((int) secondProgress);
    }

    private void setSecondProgress(int secondProgress) {
        mSeekBar.setSecondaryProgress(secondProgress);
    }

    private void setBottomSeekProgress(int curr, int duration) {
        mBottomSeekBar.setMax(duration);
        mBottomSeekBar.setProgress(curr);
        float secondProgress = mBufferPercentage * 1.0f / 100 * duration;
        mBottomSeekBar.setSecondaryProgress((int) secondProgress);
    }

    @Override
    public void onTimerUpdate(int curr, int duration, int bufferPercentage) {
        if (!mTimerUpdateProgressEnable)
            return;
        if (mTimeFormat == null || duration != mSeekBar.getMax()) {
            mTimeFormat = TimeUtil.getFormat(duration);
        }
        mBufferPercentage = bufferPercentage;
        updateUI(curr, duration);
    }

    private void updateUI(int curr, int duration) {
        setSeekProgress(curr, duration);
        setBottomSeekProgress(curr, duration);
        setCurrTime(curr);
        setTotalTime(duration);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
                mBufferPercentage = 0;
                mTimeFormat = null;
                updateUI(0, 0);
                setBottomSeekBarState(true);
                DataSource data = (DataSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                getGroupValue().putObject(KEY_DATA_SOURCE, data);
                setTitle(data);
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                int status = bundle.getInt(EventKey.INT_DATA);
                if (status == IPlayer.STATE_PAUSED) {
                    mStateIcon.setSelected(true);
                } else if (status == IPlayer.STATE_STARTED) {
                    mStateIcon.setSelected(false);
                }
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                mTimerUpdateProgressEnable = true;
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public Bundle onPrivateEvent(int eventCode, Bundle bundle) {

        if (eventCode == EVENT_CODE_UPDATE_SEEK) {
            if (bundle != null) {
                int curr = bundle.getInt(EventKey.INT_ARG1);
                int duration = bundle.getInt(EventKey.INT_ARG2);
                updateUI(curr, duration);
            }
        }
        return null;
    }


    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_controller_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelLow(1);
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        if (!mGestureEnable)
            return;
        toggleController();
    }

    @Override
    public void onDoubleTap(MotionEvent event) {
    }

    @Override
    public void onDown(MotionEvent event) {
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!mGestureEnable)
            return;
    }

    @Override
    public void onEndGesture() {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cover_player_controller_image_view_back_icon) {
            notifyReceiverEvent(EVENT_CODE_REQUEST_BACK, null);

        } else if (i == R.id.cover_player_controller_image_view_play_state) {
            boolean selected = mStateIcon.isSelected();
            if (selected) {
                requestResume(null);
            } else {
                requestPause(null);
            }
            mStateIcon.setSelected(!selected);

        } else if (i == R.id.cover_player_controller_image_view_switch_screen) {
            notifyReceiverEvent(EVENT_CODE_REQUEST_TOGGLE_SCREEN, null);
        } else if (i == R.id.small_window) {
            //开启小窗模式
            notifyReceiverEvent(EVENT_CODE_TO_FLOAT_MODE,null);
        }
    }
}
