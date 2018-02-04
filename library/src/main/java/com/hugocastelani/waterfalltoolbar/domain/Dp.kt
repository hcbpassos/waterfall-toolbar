package com.hugocastelani.waterfalltoolbar.domain

/**
 * Created by Hugo Castelani
 * Date: 23/12/17
 * Time: 22:12
 */

data class Dp(var value: Float, private val density: Float) {
    fun toPixel(): Pixel {
        return Pixel((value * density + 0.5f).toInt(), density)
    }
}