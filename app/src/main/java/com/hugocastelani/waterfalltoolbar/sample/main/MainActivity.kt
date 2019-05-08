package com.hugocastelani.waterfalltoolbar.sample.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import com.hugocastelani.waterfalltoolbar.sample.R
import com.hugocastelani.waterfalltoolbar.sample.sample.SampleActivity
import kotlinx.android.synthetic.main.activity_main.*

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        // setup toolbar
        setSupportActionBar(am_toolbar)

        // setup initial elevation seek bar
        am_initial_elevation_seek_bar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {}

            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {
                val newValue = am_initial_elevation_seek_bar.progress
                am_final_elevation_seek_bar.min = newValue
                am_final_elevation_seek_bar.max = newValue + 10
                am_final_elevation_seek_bar.invalidate()
            }
        })

        // setup restore default button
        am_restore_default_button.setOnClickListener {
            am_final_elevation_seek_bar.min = 0
            am_final_elevation_seek_bar.max = 10
            am_initial_elevation_seek_bar.progress = am_waterfall_toolbar.defaultInitialElevation.toDp().value.toInt()

            am_final_elevation_seek_bar.min = am_waterfall_toolbar.defaultInitialElevation.toDp().value.toInt()
            am_final_elevation_seek_bar.max = am_waterfall_toolbar.defaultInitialElevation.toDp().value.toInt() + 10
            am_final_elevation_seek_bar.progress = am_waterfall_toolbar.defaultFinalElevation.toDp().value.toInt()

            am_scroll_final_position_seek_bar.progress = am_waterfall_toolbar.defaultScrollFinalElevation
        }

        // setup done button
        am_done_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("initial_elevation", am_initial_elevation_seek_bar.progress)
            bundle.putInt("final_elevation", am_final_elevation_seek_bar.progress)
            bundle.putInt("scroll_final_position", am_scroll_final_position_seek_bar.progress)

            val intent = Intent(this, SampleActivity::class.java)
            intent.putExtras(bundle)

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.see_on_github) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HugoCastelani/waterfall-toolbar"))
            startActivity(browserIntent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
