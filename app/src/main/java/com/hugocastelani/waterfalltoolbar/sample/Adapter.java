package com.hugocastelani.waterfalltoolbar.sample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugocastelani.waterfalltoolbar.R;

import java.util.ArrayList;

/**
 * Created by Hugo Castelani
 * Date: 19/09/17
 * Time: 15:30
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private final ArrayList<ItemModel> itemModelList;

    public Adapter(@NonNull final ArrayList<ItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(itemModelList.get(position).getTitle());
        holder.summary.setText(itemModelList.get(position).getSummary());
    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView summary;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.title);
            summary = itemView.findViewById(android.R.id.summary);
        }
    }
}
