package com.hugocastelani.waterfalltoolbar.sample.sample

/**
 * Created by Hugo Castelani
 * Date: 20/09/17
 * Time: 17:38
 */

class ItemModel() {
    var title: String? = null
    var summary: String? = null

    constructor(title: String?, summary: String?): this() {
        this.title = title
        this.summary = summary
    }

    override fun toString(): String = "$title\n$summary"
}

fun itemModel(init: ItemModel.() -> Unit): ItemModel {
    val itemModel = ItemModel()
    itemModel.init()
    return itemModel
}