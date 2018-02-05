package com.hugocastelani.waterfalltoolbar

/**
 * Created by Hugo Castelani
 * Date: 04/02/18
 * Time: 23:20
 */

var density: Float? = null

data class Dp(var value: Float) {
    fun toPx(): Px {
        val innerDensity: Float = density ?: throw NullPointerException(
                "You must set density before using DimensionUnits classes.")
        return Px((value * innerDensity + 0.5f).toInt())
    }
}

data class Px(var value: Int) {
    fun toDp(): Dp {
        val innerDensity: Float = density ?: throw NullPointerException(
                "You must set density before using DimensionUnits classes.")
        return Dp(value / innerDensity + 0.5f)
    }
}