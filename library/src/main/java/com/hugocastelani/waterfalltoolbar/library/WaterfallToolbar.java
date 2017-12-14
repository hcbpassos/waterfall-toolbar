package com.hugocastelani.waterfalltoolbar.library;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.hugocastelani.waterfalltoolbar.library.annotation.Dp;
import com.hugocastelani.waterfalltoolbar.library.annotation.Percentage;

/**
 * Created by Hugo Castelani
 * Date: 19/09/17
 * Time: 19:30
 */

public class WaterfallToolbar extends CardView {
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;

    private Integer mInitialElevation;
    private Integer mFinalElevation;
    private Integer mScrollFinalPosition;

    public static final Integer DEFAULT_INITIAL_ELEVATION = 1;
    public static final Integer DEFAULT_FINAL_ELEVATION = 6;
    public static final Integer DEFAULT_SCROLL_FINAL_ELEVATION = 6;

    public WaterfallToolbar(Context context) {
        super(context);
        init();
    }

    public WaterfallToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterfallToolbar(Context context, AttributeSet attrs, Integer defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setRadius(0);    // same as cardCornerRadius

        setInitialElevation(DEFAULT_INITIAL_ELEVATION);
        setFinalElevation(DEFAULT_FINAL_ELEVATION);
        setScrollFinalPosition(DEFAULT_SCROLL_FINAL_ELEVATION);
    }

    /**
     * @param value The recycler view whose scroll is going to be listened
     * @return Own object
     */
    public WaterfallToolbar addRecyclerView(@NonNull final RecyclerView value) {
        mRecyclerView = value;
        addRecyclerViewScrollListener();
        return this;
    }

    /**
     * @param value The scroll view whose scroll is going to be listened
     * @return Own object
     */
    public WaterfallToolbar addScrollView(@NonNull final ScrollView value) {
        mScrollView = value;
        addScrollViewScrollListener();
        return this;
    }

    /**
     * @param value The elevation with which the toolbar starts
     * @return Own object
     */
    public WaterfallToolbar setInitialElevation(@Dp @NonNull final Integer value) {
        setCardElevation(dp2px(value));
        mInitialElevation = dp2px(value);
        return this;
    }

    /**
     * @param value The elevation the toolbar gets when it reaches final scroll elevation
     * @return Own object
     */
    public WaterfallToolbar setFinalElevation(@Dp @NonNull final Integer value) {
        mFinalElevation = dp2px(value);
        return this;
    }

    /**
     * @param value The percentage of the screen's height that is
     *              going to be scrolled to reach the final elevation
     * @return Own object
     */
    public WaterfallToolbar setScrollFinalPosition(@Percentage @NonNull final Integer value) {
        final Integer screenHeight = getResources().getDisplayMetrics().heightPixels;

        mScrollFinalPosition = dp2px((int) (screenHeight * (value / 100.0)));
        return this;
    }

    // this represents current recycler/list/scroll view's position
    private Integer mPosition = 0;

    private void addRecyclerViewScrollListener() {
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mPosition += dy;
                    onScroll();
                }
            });
        } else {
            throw new NullPointerException("RecyclerView cannot be null.");
        }
    }

    private void addScrollViewScrollListener() {
        if (mScrollView != null) {
            mScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
                mPosition = mScrollView.getScrollY();
                onScroll();
            });
        } else {
            throw new NullPointerException("ScrollView cannot be null.");
        }
    }

    private Integer onScroll() {
        Integer elevation;

        // shadow shall not increase if current position
        // is higher than scroll's final position
        if (mPosition <= mScrollFinalPosition) {
            elevation = calculateElevation();
            setCardElevation(elevation);

        } else {

            // tweak below fixes issue #1, avoiding elevation
            // setting problems when fast scrolling
            final Integer mPositionBackup = mPosition;
            mPosition = mScrollFinalPosition;
            elevation = calculateElevation();
            mPosition = mPositionBackup;

            if (getCardElevation() != elevation) {
                setCardElevation(elevation);
            }
        }

        // going to avoid extra code to get current elevation in onSaveInstanceState method
        return elevation;
    }

    private Integer calculateElevation() {
        // getting back to rule of three:
        // mFinalElevation (px) = mScrollFinalPosition (px)
        // newElevation    (px) = mPosition            (px)
        Integer newElevation = (mFinalElevation * mPosition) / mScrollFinalPosition;

        // avoid values under minimum value
        if (newElevation < mInitialElevation) newElevation = mInitialElevation;

        return newElevation;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.setElevation(onScroll());
        savedState.setPosition(mPosition);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        // setCardElevation method doesn't work until view is created
        post(() -> setCardElevation(savedState.getElevation()));
        mPosition = savedState.getPosition();
    }

    private static class SavedState extends BaseSavedState {
        private Integer elevation;
        private Integer position;

        SavedState(Parcel source) {
            super(source);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @NonNull
        Integer getElevation() {
            return elevation;
        }

        void setElevation(@NonNull final Integer elevation) {
            this.elevation = elevation;
        }

        @NonNull
        public Integer getPosition() {
            return position;
        }

        public void setPosition(@NonNull final Integer position) {
            this.position = position;
        }

        static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * <pre>
     *     author: Blankj
     *     blog  : http://blankj.com
     *     time  : 2016/08/13
     *     desc  : 转换相关工具类
     * </pre>
     *
     * Method got from:
     * https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
     */
    private Integer dp2px(@Dp @NonNull final Integer dpValue) {
        final Float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}