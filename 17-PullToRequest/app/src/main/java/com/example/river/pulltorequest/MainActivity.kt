package com.example.river.pulltorequest

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var list = mutableListOf<InfoItem>()
    lateinit var cusAdapter: CusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        initSwipeRefreshLayout()
    }

    fun initAdapter() {
        cusAdapter = CusAdapter(list)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = cusAdapter
    }

    fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setProgressViewOffset(true, 0, 100)
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE)

        swipeRefreshLayout.setOnRefreshListener {
            Thread.sleep(200)

            cusAdapter.addListItem(getRandomColor())

            swipeRefreshLayout.isRefreshing = false
        }

    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.rgb(
            rnd.nextInt(256),
            rnd.nextInt(256),
            rnd.nextInt(256)
        )
    }
}
