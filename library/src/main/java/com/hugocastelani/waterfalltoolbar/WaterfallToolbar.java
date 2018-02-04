package com.hugocastelani.waterfalltoolbar;

import android.content.Context;
import android.content.res.TypedArray;
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

import com.hugocastelani.waterfalltoolbar.domain.Dp;
import com.hugocastelani.waterfalltoolbar.domain.Pixel;

/**
 * Created by Hugo Castelani
 * Date: 19/09/17
 * Time: 19:30
 */

public class WaterfallToolbar extends CardView {
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;

    private Pixel mInitialElevation;
    private Pixel mFinalElevation;
    private Integer mScrollFinalPosition;

    private final float density = getResources().getDisplayMetrics().density;

    public final Dp defaultInitialElevation = new Dp(0f, density);
    public final Dp defaultFinalElevation = new Dp(4f, density);
    public final Integer defaultScrollFinalElevation = 12;

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
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterfallToolbar);

            final Integer initialElevation = typedArray.getDimensionPixelSize(
                    R.styleable.WaterfallToolbar_initial_elevation,
                    defaultInitialElevation.toPixel().getValue());

            final Integer finalElevation = typedArray.getDimensionPixelSize(
                    R.styleable.WaterfallToolbar_final_elevation,
                    defaultFinalElevation.toPixel().getValue());

            final Integer scrollFinalPosition = typedArray.getInteger(
                    R.styleable.WaterfallToolbar_scroll_final_elevation,
                    defaultScrollFinalElevation);

            setInitialElevation(new Pixel(initialElevation, density));
            setFinalElevation(new Pixel(finalElevation, density));
            setScrollFinalPosition(scrollFinalPosition);

            typedArray.recycle();

        } else {

            setInitialElevation(defaultInitialElevation.toPixel());
            setFinalElevation(defaultFinalElevation.toPixel());
            setScrollFinalPosition(defaultScrollFinalElevation);
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
     * @param pixel The elevation with which the toolbar starts
     * @return Own object
     */
    public WaterfallToolbar setInitialElevation(@NonNull final Pixel pixel) {
        mInitialElevation = pixel;

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    /**
     * @param pixel The elevation the toolbar gets when it reaches final scroll elevation
     * @return Own object
     */
    public WaterfallToolbar setFinalElevation(@NonNull final Pixel pixel) {
        mFinalElevation = pixel;

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
        mScrollFinalPosition = Math.round(screenHeight * (value / 100.0f));

        // gotta update elevation in case this value have
        // been set in a running and visible activity
        if (mIsSetup) adjustCardElevation();

        return this;
    }

    // position in which toolbar must be to reach expected shadow
    private Pixel mOrthodoxPosition = new Pixel(0, density);

    // recycler/scroll view real position
    private Pixel mRealPosition = new Pixel(0, density);

    private void addRecyclerViewScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // real position must always get updated
                mRealPosition.setValue(mRealPosition.getValue() + dy);
                mutualScrollListenerAction();
            }
        });
    }

    private void addScrollViewScrollListener() {
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            // real position must always get updated
            mRealPosition.setValue(mScrollView.getScrollY());
            mutualScrollListenerAction();
        });
    }

    /**
     * These lines are common in both scroll listeners, so they are better joined
     */
    private void mutualScrollListenerAction() {
        // mOrthodoxPosition can't be higher than mScrollFinalPosition because
        // the last one holds the position in which shadow reaches ideal size

        if (mRealPosition.getValue() <= mScrollFinalPosition) {
            mOrthodoxPosition.setValue(mRealPosition.getValue());
        } else {
            mOrthodoxPosition.setValue(mScrollFinalPosition);
        }

        adjustCardElevation();
    }

    /**
     * Speed up the card elevation setting
     */
    public void adjustCardElevation() {
        setCardElevation(calculateElevation().getValue());
    }

    /**
     * Calculates the elevation based on given attributes and scroll
     * @return New calculated elevation
     */
    private Pixel calculateElevation() {
        // getting back to rule of three:
        // mFinalElevation = mScrollFinalPosition
        // newElevation    = mOrthodoxPosition
        Integer newElevation = (mFinalElevation.getValue() * mOrthodoxPosition.getValue()) / mScrollFinalPosition;

        // avoid values under minimum value
        if (newElevation < mInitialElevation.getValue()) newElevation = mInitialElevation.getValue();

        return new Pixel(newElevation, density);
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
        private Integer elevation;
        private Pixel orthodoxPosition;
        private Pixel realPosition;

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
        Pixel getOrthodoxPosition() {
            return orthodoxPosition;
        }

        SavedState setOrthodoxPosition(@NonNull final Pixel orthodoxPosition) {
            this.orthodoxPosition = orthodoxPosition;
            return this;
        }

        @NonNull
        Pixel getRealPosition() {
            return realPosition;
        }

        SavedState setRealPosition(@NonNull final Pixel realPosition) {
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
}