package com.hugocastelani.waterfalltoolbar.sample.sample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hugocastelani.waterfalltoolbar.WaterfallToolbar;
import com.hugocastelani.waterfalltoolbar.domain.Dp;
import com.hugocastelani.waterfalltoolbar.sample.R;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {
    private WaterfallToolbar mWaterfallToolbar;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private Float mInitialElevation;
    private Float mFinalElevation;
    private Integer mScrollFinalPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mInitialElevation = (float) bundle.getInt("initial_elevation");
            mFinalElevation = (float) bundle.getInt("final_elevation");
            mScrollFinalPosition = bundle.getInt("scroll_final_position");
        }

        initViews();
        setupViews();
    }

    private void initViews() {
        mWaterfallToolbar = findViewById(R.id.as_waterfall_toolbar);
        mToolbar = findViewById(R.id.as_toolbar);
        mRecyclerView = findViewById(R.id.as_recycler_view);
    }

    private void setupViews() {
        final float density = getResources().getDisplayMetrics().density;

        // setup waterfall toolbar
        mWaterfallToolbar.addRecyclerView(mRecyclerView)
                // setters below aren't mandatory
                .setInitialElevation(new Dp(mInitialElevation, density).toPixel())
                .setFinalElevation(new Dp(mFinalElevation, density).toPixel())
                .setScrollFinalPosition(mScrollFinalPosition);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(
                this, R.drawable.ic_arrow_back));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup recycler view
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false);

        final Adapter adapter = new Adapter(getSetItemModelList());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private static ArrayList<ItemModel> getSetItemModelList() {
        final ArrayList<ItemModel> itemModelList = new ArrayList<>();

        for (Integer i = 0; i < 20;) {
            itemModelList.add(new ItemModel(
                    "Item #" + (++i),
                    "Earth isn't flat"
            ));
        }

        return itemModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
