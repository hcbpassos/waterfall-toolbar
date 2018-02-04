package com.hugocastelani.waterfalltoolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ScrollView;

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

    public static final Float DEFAULT_INITIAL_ELEVATION_DP = 0f;
    public static final Float DEFAULT_FINAL_ELEVATION_DP = 4f;
    public static final Integer DEFAULT_SCROLL_FINAL_ELEVATION = 6;

    public Boolean mIsSetup = false;

    public WaterfallToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public WaterfallToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WaterfallToolbar(Context context, AttributeSet attrs, Integer defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, @Nullable final AttributeSet attrs) {
        setRadius(0);    // same as cardCornerRadius

        if (context != null && attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, com.hugocastelani.waterfalltoolbar.R.styleable.WaterfallToolbar);

            setInitialElevationPx(typedArray.getDimensionPixelSize(com.hugocastelani.waterfalltoolbar.R.styleable.WaterfallToolbar_initial_elevation, dp2px(DEFAULT_INITIAL_ELEVATION_DP)));
            setFinalElevationPx(typedArray.getDimensionPixelSize(com.hugocastelani.waterfalltoolbar.R.styleable.WaterfallToolbar_final_elevation, dp2px(DEFAULT_FINAL_ELEVATION_DP)));
            setScrollFinalPosition(typedArray.getInteger(com.hugocastelani.waterfalltoolbar.R.styleable.WaterfallToolbar_scroll_final_elevation, DEFAULT_SCROLL_FINAL_ELEVATION));

            typedArray.recycle();

        } else {

            setInitialElevationDp(DEFAULT_INITIAL_ELEVATION_DP);
            setFinalElevationDp(DEFAULT_FINAL_ELEVATION_DP);
            setScrollFinalPosition(DEFAULT_SCROLL_FINAL_ELEVATION);
        }

        // just to make sure card elevation is set
        adjustCardElevation();

        mIsSetup = true;
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
     * @param value The elevation (in dp) with which the toolbar starts
     * @return Own object
     */
    public WaterfallToolbar setInitialElevationDp(@NonNull final Float value) {
        mInitialElevation = dp2px(value);

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    /**
     * @param value The elevation (in px) with which the toolbar starts
     * @return Own object
     */
    public WaterfallToolbar setInitialElevationPx(@Px @NonNull final Integer value) {
        mInitialElevation = value;

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    /**
     * @param value The elevation (in dp) the toolbar gets when it reaches final scroll elevation
     * @return Own object
     */
    public WaterfallToolbar setFinalElevationDp(@NonNull final Float value) {
        mFinalElevation = dp2px(value);

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    /**
     * @param value The elevation (in px) the toolbar gets when it reaches final scroll elevation
     * @return Own object
     */
    public WaterfallToolbar setFinalElevationPx(@Px @NonNull final Integer value) {
        mFinalElevation = value;

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    /**
     * @param value The percentage of the screen's height that is
     *              going to be scrolled to reach the final elevation
     * @return Own object
     */
    public WaterfallToolbar setScrollFinalPosition(@NonNull final Integer value) {
        final Integer screenHeight = getResources().getDisplayMetrics().heightPixels;
        mScrollFinalPosition = dp2px(screenHeight * (value / 100.0f));

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    // position in which toolbar must be to reach expected shadow
    private Integer mOrthodoxPosition = 0;

    // recycler/scroll view real position
    private Integer mRealPosition = 0;

    private void addRecyclerViewScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // real position must always get updated
                mRealPosition += dy;
                mutualScrollListenerAction();
            }
        });
    }

    private void addScrollViewScrollListener() {
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            // real position must always get updated
            mRealPosition = mScrollView.getScrollY();
            mutualScrollListenerAction();
        });
    }

    /**
     * These lines are common in both scroll listeners, so they are better joined
     */
    private void mutualScrollListenerAction() {
        // mOrthodoxPosition can't be higher than mScrollFinalPosition because
        // the last one holds the position in which shadow reaches ideal size

        if (mRealPosition <= mScrollFinalPosition) {
            mOrthodoxPosition = mRealPosition;
        } else {
            mOrthodoxPosition = mScrollFinalPosition;
        }

        adjustCardElevation();
    }

    /**
     * Speed up the card elevation setting
     */
    public void adjustCardElevation() {
        setCardElevation(calculateElevation());
    }

    /**
     * Calculates the elevation based on given attributes and scroll
     * @return New calculated elevation
     */
    private Integer calculateElevation() {
        // getting back to rule of three:
        // mFinalElevation (px) = mScrollFinalPosition (px)
        // newElevation    (px) = mOrthodoxPosition    (px)
        Integer newElevation = (mFinalElevation * mOrthodoxPosition) / mScrollFinalPosition;

        // avoid values under minimum value
        if (newElevation < mInitialElevation) newElevation = mInitialElevation;

        return newElevation;
    }

    /**
     * Saves the view's current dynamic state in a parcelable object
     * @return A parcelable with the saved data
     */
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final SavedState savedState = new SavedState(super.onSaveInstanceState());

        savedState.setElevation((int) getCardElevation())
                .setOrthodoxPosition(mOrthodoxPosition)
                .setRealPosition(mRealPosition);

        return savedState;
    }

    /**
     * Restore the view's dynamic state
     * @param state The frozen state that had previously been returned by onSaveInstanceState()
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        // setCardElevation() doesn't work until view is created
        post(() -> {
            setCardElevation(savedState.getElevation());
            mOrthodoxPosition = savedState.getOrthodoxPosition();
            mRealPosition = savedState.getRealPosition();
        });
    }

    /**
     * Custom parcelable to store this view's dynamic state
     */
    private static class SavedState extends BaseSavedState {
        @Px private Integer elevation;
        private Integer orthodoxPosition;
        private Integer realPosition;

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

        SavedState setElevation(@NonNull final Integer elevation) {
            this.elevation = elevation;
            return this;
        }

        @NonNull
        Integer getOrthodoxPosition() {
            return orthodoxPosition;
        }

        SavedState setOrthodoxPosition(@NonNull final Integer orthodoxPosition) {
            this.orthodoxPosition = orthodoxPosition;
            return this;
        }

        @NonNull
        Integer getRealPosition() {
            return realPosition;
        }

        SavedState setRealPosition(@NonNull final Integer realPosition) {
            this.realPosition = realPosition;
            return this;
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
     * Copyright 2017 Blankj
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     * http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     *
     * Modifications: add some annotations, replace primitive types
     * by objects, remove staticity and adapt resources retrieval
     *
     * Method got from:
     * https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
     */
    private Integer dp2px(@NonNull final Float dpValue) {
        final Float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}