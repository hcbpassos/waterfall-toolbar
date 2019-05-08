package com.hugocastelani.waterfalltoolbar.sample.sample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hugocastelani.waterfalltoolbar.Dp
import com.hugocastelani.waterfalltoolbar.density
import com.hugocastelani.waterfalltoolbar.sample.R
import kotlinx.android.synthetic.main.activity_sample.*
import java.util.*

class SampleActivity : AppCompatActivity() {
    private var initialElevation: Float? = null
    private var finalElevation: Float? = null
    private var scrollFinalPosition: Int? = null

    private val itemModelList = ArrayList<ItemModel>()
        get() {
            (1 until 21).mapTo(field) {
                itemModel {
                    title = "Item #$it"
                    summary = "Hello, World"
                }
            }

            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val bundle = intent.extras
        if (bundle != null) {
            initialElevation = bundle.getInt("initial_elevation").toFloat()
            finalElevation = bundle.getInt("final_elevation").toFloat()
            scrollFinalPosition = bundle.getInt("scroll_final_position")

            setupViews()
        }
    }

    private fun setupViews() {
        density = resources.displayMetrics.density

        // setup waterfall toolbar
        as_waterfall_toolbar.recyclerView = as_recycler_view

        // setters below aren't mandatory
        // it's safe to use "!!" here, since this method is
        // going to be called only if the fields were assigned
        as_waterfall_toolbar.initialElevation = Dp(initialElevation!!).toPx()
        as_waterfall_toolbar.finalElevation = Dp(finalElevation!!).toPx()
        as_waterfall_toolbar.scrollFinalPosition = scrollFinalPosition

        // setup toolbar
        setSupportActionBar(as_toolbar)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(
                this, R.drawable.ic_arrow_back))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // setup recycler view
        val layoutManager = LinearLayoutManager(
                baseContext, RecyclerView.VERTICAL, false)

        val adapter = Adapter(itemModelList)

        as_recycler_view.layoutManager = layoutManager
        as_recycler_view.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
