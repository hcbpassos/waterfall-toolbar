package com.hugocastelani.waterfalltoolbar.sample.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.hugocastelani.waterfalltoolbar.WaterfallToolbar;
import com.hugocastelani.waterfalltoolbar.sample.R;
import com.hugocastelani.waterfalltoolbar.sample.sample.SampleActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private DiscreteSeekBar mInitialElevationSeekBar;
    private DiscreteSeekBar mFinalElevationSeekBar;
    private DiscreteSeekBar mScrollFinalPositionSeekBar;

    private Button mDoneButton;
    private Button mRestoreDefaultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupViews();
    }

    private void initViews() {
        mToolbar = findViewById(R.id.am_waterfall_toolbar);
        mInitialElevationSeekBar = findViewById(R.id.am_initial_elevation_seek_bar);
        mFinalElevationSeekBar = findViewById(R.id.am_final_elevation_seek_bar);
        mScrollFinalPositionSeekBar = findViewById(R.id.am_scroll_final_position_seek_bar);
        mDoneButton = findViewById(R.id.am_done_button);
        mRestoreDefaultButton = findViewById(R.id.am_restore_default_button);
    }

    private void setupViews() {
        // setup toolbar
        setSupportActionBar(mToolbar);

        // setup initial elevation seek bar
        mInitialElevationSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {}
            @Override public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                final int newValue = mInitialElevationSeekBar.getProgress();
                mFinalElevationSeekBar.setMin(newValue);
                mFinalElevationSeekBar.setMax(newValue + 10);
                mFinalElevationSeekBar.invalidate();
            }
        });

        // setup restore default button
        mRestoreDefaultButton.setOnClickListener(view -> {
            mFinalElevationSeekBar.setMin(0);
            mFinalElevationSeekBar.setMax(10);
            mInitialElevationSeekBar.setProgress(WaterfallToolbar.DEFAULT_INITIAL_ELEVATION_DP.intValue());

            mFinalElevationSeekBar.setMin(WaterfallToolbar.DEFAULT_INITIAL_ELEVATION_DP.intValue());
            mFinalElevationSeekBar.setMax(WaterfallToolbar.DEFAULT_INITIAL_ELEVATION_DP.intValue() + 10);
            mFinalElevationSeekBar.setProgress(WaterfallToolbar.DEFAULT_FINAL_ELEVATION_DP.intValue());

            mScrollFinalPositionSeekBar.setProgress(WaterfallToolbar.DEFAULT_SCROLL_FINAL_ELEVATION);
        });

        // setup done button
        mDoneButton.setOnClickListener(view -> {
            final Bundle bundle = new Bundle();
            bundle.putInt("initial_elevation", mInitialElevationSeekBar.getProgress());
            bundle.putInt("final_elevation", mFinalElevationSeekBar.getProgress());
            bundle.putInt("scroll_final_position", mScrollFinalPositionSeekBar.getProgress());

            final Intent intent = new Intent(this, SampleActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.see_on_github) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HugoCastelani/waterfall-toolbar"));
            startActivity(browserIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
