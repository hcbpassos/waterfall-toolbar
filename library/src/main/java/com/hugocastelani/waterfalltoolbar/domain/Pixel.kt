package com.hugocastelani.waterfalltoolbar.domain

/**
 * Created by Hugo Castelani
 * Date: 23/12/17
 * Time: 22:07
 */

data class Pixel(var value: Int, private val density: Float) {
    fun toDp(): Dp {
        return Dp((value / density + 0.5f), density)
    }
}