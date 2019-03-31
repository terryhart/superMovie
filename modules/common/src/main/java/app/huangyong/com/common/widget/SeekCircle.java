package app.huangyong.com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SeekCircle extends ProgressCircle {
    /**
     * A callback that notifies clients when the progress level has been
     * changed. This includes changes that were initiated by the user through a
     * touch gesture or arrow key/trackball as well as changes that were
     * initiated programmatically.
     */
    public interface OnSeekCircleChangeListener {

        /**
         * Notification that the progress level has changed. Clients can use the
         * fromUser parameter to distinguish user-initiated changes from those
         * that occurred programmatically.
         *
         * @param seekCircle The SeekCircle whose progress has changed
         * @param progress   The current progress level. This will be in the range
         *                   0..max where max was set by
         *                   {@link ProgressCircle#setMax(int)}. (The default value for
         *                   max is 100.)
         * @param fromUser   True if the progress change was initiated by the user.
         */
        void onProgressChanged(SeekCircle seekCircle, int progress, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may
         * want to use this to disable advancing the seek circle.
         *
         * @param seekCircle The SeekCircle in which the touch gesture began
         */
        void onStartTrackingTouch(SeekCircle seekCircle);

        /**
         * Notification that the user has finished a touch gesture. Clients may
         * want to use this to re-enable advancing the seek circle.
         *
         * @param seekCircle The SeekCircle in which the touch gesture began
         */
        void onStopTrackingTouch(SeekCircle seekCircle);
    }

    private OnSeekCircleChangeListener mOnSeekCircleChangeListener;

    /**
     * Sets a listener to receive notifications of changes to the SeekCircle's
     * progress level. Also provides notifications of when the user starts and
     * stops a touch gesture within the SeekCircle.
     *
     * @param listener The seek circle notification listener
     * @see OnSeekCircleChangeListener
     */
    public void setOnSeekCircleChangeListener(OnSeekCircleChangeListener listener) {
        mOnSeekCircleChangeListener = listener;
    }

    private boolean mTrackingTouch = false;
    private int mRevolutions = 0;
    private float mOldX;
    private boolean mEnabled = true;

    public SeekCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SeekCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekCircle(Context context) {
        super(context);
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Handle return value better.
        // TODO Moving outside view doesn't cancel notification nor progress updates

        if (!mEnabled) {
            return false;
        }

        // Right hand coordinates X to the right, Y up
        float x = event.getX() - mCenterX;
        float y = mCenterY - event.getY();

        float distance = (float) Math.sqrt(x * x + y * y);

        boolean inRange = Math.abs(distance - mRadius) <= mSectionHeight;

        boolean updateProgress = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (inRange) {
                    mTrackingTouch = true;
                    mOldX = x;
                    mRevolutions = 0;
                    updateProgress = true;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (mTrackingTouch)
                    updateProgress = true;
                break;

            case MotionEvent.ACTION_UP:
                if (mTrackingTouch)
                    updateProgress = true;
                mTrackingTouch = false;
                break;

            case MotionEvent.ACTION_CANCEL:
                mTrackingTouch = false;
                break;
        }

        int relativeMax = mMaxProgress - mMinProgress;

        if (updateProgress) {
            // Calculate absolute position [0, 1] with 0 & 1 both at 12-o-clock
            float position = (float) ((Math.atan2(-x, -y) + Math.PI) / (Math.PI * 2.0));
            int relativeProgress = Math.round(position * ((float) relativeMax));

            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                updateRevolutions(x, y);

                float absPosition = (float) mRevolutions + position;

                // Clamp progress
                if (absPosition < 0.0f)
                    relativeProgress = 0;
                else if (absPosition > 1.0f)
                    relativeProgress = relativeMax;
            }

            mOldX = x;
            updateTouchProgress(mMinProgress + relativeProgress);

            if (event.getAction() == MotionEvent.ACTION_DOWN && mOnSeekCircleChangeListener != null) {
                mOnSeekCircleChangeListener.onStartTrackingTouch(this);
            } else if (event.getAction() == MotionEvent.ACTION_UP && mOnSeekCircleChangeListener != null) {
                mOnSeekCircleChangeListener.onStopTrackingTouch(this);
            }

            return true; //super.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    /**
     * Update the number of revolutions we're at
     *
     * @param x X position
     * @param y Y position
     */
    private void updateRevolutions(float x, float y) {
        // We're in the upper half and X just flipped
        boolean leftFlip = y > 0.0f && mOldX >= 0.0f && x < 0.0f;
        boolean rightFlip = y > 0.0f && mOldX <= 0.0f && x > 0.0f;

        if (leftFlip)
            mRevolutions -= 1.0f;
        else if (rightFlip)
            mRevolutions += 1.0f;

        // Clamp windings to [-1, 1]
        mRevolutions = Math.max(-1, Math.min(1, mRevolutions));
    }

    @Override
    protected boolean updateProgress(int progress) {
        boolean result = super.updateProgress(progress);
        if (result) {
            // Reset position to match
            if (mOnSeekCircleChangeListener != null)
                mOnSeekCircleChangeListener.onProgressChanged(this, progress, false);
        }

        return result;
    }

    private void updateTouchProgress(int progress) {
        boolean result = super.updateProgress(progress);
        if (result) {
            if (mOnSeekCircleChangeListener != null)
                mOnSeekCircleChangeListener.onProgressChanged(this, progress, true);
        }
    }
}
