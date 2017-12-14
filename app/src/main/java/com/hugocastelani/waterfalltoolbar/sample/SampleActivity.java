package com.hugocastelani.waterfalltoolbar.sample;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hugocastelani.waterfalltoolbar.R;
import com.hugocastelani.waterfalltoolbar.library.WaterfallToolbar;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {
    WaterfallToolbar mWaterfallToolbar;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;

    Float mInitialElevation;
    Float mFinalElevation;
    Integer mScrollFinalPosition;

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
        prepareViews();
    }

    private void initViews() {
        mWaterfallToolbar = findViewById(R.id.waterfall_toolbar);
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void prepareViews() {
        // prepare waterfall toolbar
        mWaterfallToolbar.addRecyclerView(mRecyclerView)
                // setters below aren't mandatory
                .setInitialElevationDp(mInitialElevation)
                .setFinalElevationDp(mFinalElevation)
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
