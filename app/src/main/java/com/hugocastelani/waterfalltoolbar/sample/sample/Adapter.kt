package com.hugocastelani.waterfalltoolbar.sample.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hugocastelani.waterfalltoolbar.sample.R
import kotlinx.android.synthetic.main.item.view.*
import java.util.*

/**
 * Created by Hugo Castelani
 * Date: 19/09/17
 * Time: 15:30
 */

class Adapter(private val itemModelList: ArrayList<ItemModel>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = itemModelList[position].title
        holder.summary.text = itemModelList[position].summary
    }

    override fun getItemCount(): Int {
        return itemModelList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val summary: TextView = itemView.summary
    }
}