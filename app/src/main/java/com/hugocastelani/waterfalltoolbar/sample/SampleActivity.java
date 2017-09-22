package com.hugocastelani.waterfalltoolbar.sample;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hugocastelani.waterfalltoolbar.R;
import com.hugocastelani.waterfalltoolbar.library.WaterfallToolbar;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {
    WaterfallToolbar mWaterfallToolbar;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;

    int mInitialElevation;
    int mFinalElevation;
    int mScrollFinalPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mInitialElevation = bundle.getInt("initial_elevation");
            mFinalElevation = bundle.getInt("final_elevation");
            mScrollFinalPosition = bundle.getInt("scroll_final_position");
        }

        initViews();
        prepareViews();
    }

    private void initViews() {
        mWaterfallToolbar = (WaterfallToolbar) findViewById(R.id.waterfall_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void prepareViews() {
        // prepare waterfall toolbar
        mWaterfallToolbar.addRecyclerView(mRecyclerView)
                // setters below aren't mandatory
                .setInitialElevation(mInitialElevation)
                .setFinalElevation(mFinalElevation)
                .setScrollFinalPosition(mScrollFinalPosition);

        // prepare toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // prepare recycler view
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false);

        final Adapter adapter = new Adapter(generateItemModelList());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private static ArrayList<ItemModel> generateItemModelList() {
        final ArrayList<ItemModel> itemModelList = new ArrayList<>();

        for (int i = 0; i < 20;) {
            itemModelList.add(new ItemModel(
                    "Item #" + (++i),
                    "Earth isn't flat"
            ));
        }

        return itemModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
