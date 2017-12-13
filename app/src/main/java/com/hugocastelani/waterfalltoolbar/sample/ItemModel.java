package com.hugocastelani.waterfalltoolbar.sample;

import android.support.annotation.NonNull;

/**
 * Created by Hugo Castelani
 * Date: 20/09/17
 * Time: 17:38
 */

public class ItemModel {
    private String title;
    private String summary;

    public ItemModel(@NonNull final String title, @NonNull final String summary) {
        this.title = title;
        this.summary = summary;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull final String title) {
        this.title = title;
    }

    @NonNull
    public String getSummary() {
        return summary;
    }

    public void setSummary(@NonNull final String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return title + "\n" + summary;
    }
}
