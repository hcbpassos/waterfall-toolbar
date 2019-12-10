package com.hugocastelani.waterfalltoolbar

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Hugo Castelani
 * Date: 19/09/17
 * Time: 19:30
 */

open class WaterfallToolbar : CardView {
    init {
        // set density to be able to use DimensionUnits
        // this code must run before all the signings using DimensionUnits
        if (density == null) density = resources.displayMetrics.density
    }

    /**
     * The recycler view whose scroll is going to be listened
     */
    var recyclerView: RecyclerView? = null
        set(value) {
            resetElevation()
            if (value == null) {
                unbindRecyclerView()
                field = value
            } else {
                field = value
                addRecyclerViewScrollListener()
            }
        }

    private val recyclerViewScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // real position must always get updated

                    realPosition.value = realPosition.value + dy
                    mutualScrollListenerAction()
                }
            }

    fun resetElevation() {
        realPosition.value = 0
        orthodoxPosition.value = 0
        adjustCardElevation()
    }

    /**
     * The scroll view whose scroll is going to be listened
     */
    var scrollView: ScrollView? = null
        set(value) {
            if (value == null) {
                unbindScrollView()
                field = value
            } else {
                field = value
                addScrollViewScrollListener()
            }
        }

    private val scrollViewScrollListener: ViewTreeObserver.OnScrollChangedListener =
            ViewTreeObserver.OnScrollChangedListener {
                scrollView?.let {
                    // real position must always get updated
                    realPosition.value = it.scrollY
                    mutualScrollListenerAction()
                }
            }

    /**
     * The three variables ahead are null safe, since they are always set
     * at least once in init() and a null value can't be assigned to them
     * after that. So all the "!!" involving them below are fully harmless.
     */

    /**
     * The elevation with which the toolbar starts
     */
    var initialElevation: Px? = null
        set(value) {
            if (value != null) {
                field = value

                // got to update elevation in case this value have
                // been set in a running and visible activity
                if (isSetup) adjustCardElevation()

            } else throw NullPointerException("This field cannot be null.")
        }

    /**
     * The elevation the toolbar gets when it reaches final scroll elevation
     */
    var finalElevation: Px? = null
        set(value) {
            if (value != null) {
                field = value

                // got to update elevation in case this value have
                // been set in a running and visible activity
                if (isSetup) adjustCardElevation()

            } else throw NullPointerException("This field cannot be null.")
        }

    /**
     * The percentage of the screen's height that is
     * going to be scrolled to reach the final elevation
     */
    var scrollFinalPosition: Int? = null
        set(value) {
            if (value != null) {
                val screenHeight = resources.displayMetrics.heightPixels
                field = Math.round(screenHeight * (value / 100.0f))

                // got to update elevation in case this value have
                // been set in a running and visible activity
                if (isSetup) adjustCardElevation()

            } else throw NullPointerException("This field cannot be null.")
        }

    /**
     * Dimension units (dp and pixel) auxiliary
     */


    /**
     * Values related to Waterfall Toolbar behavior in their default forms
     */
    val defaultInitialElevation = Dp(0f).toPx()
    val defaultFinalElevation = Dp(4f).toPx()
    val defaultScrollFinalElevation = 12

    /**
     * Auxiliary that indicates if the view is already setup
     */
    private var isSetup: Boolean = false

    /**
     * Position in which toolbar must be to reach expected shadow
     */
    private var orthodoxPosition = Px(0)

    /**
     * Recycler/scroll view real position
     */
    private var realPosition = Px(0)

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int?)
            : super(context, attrs, defStyleAttr!!) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        // leave card corners square
        radius = 0f

        if (context != null && attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterfallToolbar)

            val rawInitialElevation = typedArray.getDimensionPixelSize(
                    R.styleable.WaterfallToolbar_initial_elevation, defaultInitialElevation.value)

            val rawFinalElevation = typedArray.getDimensionPixelSize(
                    R.styleable.WaterfallToolbar_final_elevation, defaultFinalElevation.value)

            scrollFinalPosition = typedArray.getInteger(
                    R.styleable.WaterfallToolbar_scroll_final_elevation, defaultScrollFinalElevation)

            this.initialElevation = Px(rawInitialElevation)
            this.finalElevation = Px(rawFinalElevation)

            typedArray.recycle()

        } else {

            initialElevation = defaultInitialElevation
            finalElevation = defaultFinalElevation
            scrollFinalPosition = defaultScrollFinalElevation
        }

        adjustCardElevation()    // just to make sure card elevation is set

        isSetup = true
    }

    private fun addRecyclerViewScrollListener() =
            recyclerView?.addOnScrollListener(recyclerViewScrollListener)

    fun unbindRecyclerView() = recyclerView?.removeOnScrollListener(recyclerViewScrollListener)

    private fun addScrollViewScrollListener() =
            scrollView?.viewTreeObserver?.addOnScrollChangedListener(scrollViewScrollListener)

    fun unbindScrollView() =
            scrollView?.viewTreeObserver?.removeOnScrollChangedListener(scrollViewScrollListener)

    /**
     * These lines are common in both scroll listeners, so they are better joined
     */
    private fun mutualScrollListenerAction() {
        // orthodoxPosition can't be higher than scrollFinalPosition because
        // the last one holds the position in which shadow reaches ideal size

        if (realPosition.value <= scrollFinalPosition!!) {
            orthodoxPosition.value = realPosition.value
        } else {
            orthodoxPosition.value = scrollFinalPosition!!
        }

        adjustCardElevation()
    }

    /**
     * Speed up the card elevation setting
     */
    private fun adjustCardElevation() {
        cardElevation = calculateElevation().value.toFloat()
    }

    /**
     * Calculates the elevation based on given attributes and scroll
     * @return New calculated elevation
     */
    private fun calculateElevation(): Px {
        // getting back to rule of three:
        // finalElevation = scrollFinalPosition
        // newElevation   = orthodoxPosition
        var newElevation: Int = finalElevation!!.value * orthodoxPosition.value / scrollFinalPosition!!

        // avoid values under minimum value
        if (newElevation < initialElevation!!.value) newElevation = initialElevation!!.value

        return Px(newElevation)
    }

    /**
     * Saves the view's current dynamic state in a parcelable object
     * @return A parcelable with the saved data
     */
    override fun onSaveInstanceState(): Parcelable {
        val savedState = SavedState(super.onSaveInstanceState())

        savedState.elevation = cardElevation.toInt()
        savedState.orthodoxPosition = orthodoxPosition
        savedState.realPosition = realPosition

        return savedState
    }

    /**
     * Restore the view's dynamic state
     * @param state The frozen state that had previously been returned by onSaveInstanceState()
     */
    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)

            // setting card elevation doesn't work until view is created
            post {
                // it's safe to use "!!" here, since savedState will
                // always store values properly set in onSaveInstanceState()
                cardElevation = state.elevation?.toFloat() ?: 0f
                orthodoxPosition = state.orthodoxPosition ?: Px(0)
                realPosition = state.realPosition ?: Px(0)
            }

        } else {

            super.onRestoreInstanceState(state)
        }
    }

    /**
     * Custom parcelable to store this view's dynamic state
     */
    private class SavedState : BaseSavedState {
        var elevation: Int? = null
        var orthodoxPosition: Px? = null
        var realPosition: Px? = null

        internal constructor(source: Parcel) : super(source)

        @RequiresApi(api = Build.VERSION_CODES.N)
        internal constructor(source: Parcel, loader: ClassLoader) : super(source, loader)

        internal constructor(superState: Parcelable?) : super(superState)

        companion object {
            @JvmField
            internal val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
