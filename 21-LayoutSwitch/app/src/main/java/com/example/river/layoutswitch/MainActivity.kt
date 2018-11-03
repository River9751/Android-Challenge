package com.example.river.layoutswitch

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainActivity : AppCompatActivity() {

    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var cusAdapter: CusAdapter
    lateinit var list: MutableList<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayoutManager = GridLayoutManager(this, 2)
        list = getDataList()
        cusAdapter = CusAdapter(this, list, gridLayoutManager)
        recyclerView.adapter = cusAdapter
        recyclerView.layoutManager = gridLayoutManager
    }

    private fun getDataList(): MutableList<ItemModel> {
        val list = mutableListOf<ItemModel>()
        for (i in 1 until 10) {
            list.add(ItemModel("Item $i", i * 5, i * 6))
        }
        return list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_switch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_switch) {
            val spanCount = gridLayoutManager.spanCount
            when (spanCount) {
                1 -> {
                    gridLayoutManager.spanCount = 2
                    item.icon = resources.getDrawable(R.drawable.ic_menu_black_24dp, null)
                }
                2 -> {
                    gridLayoutManager.spanCount = 1
                    item.icon = resources.getDrawable(R.drawable.ic_apps_black_24dp, null)
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}