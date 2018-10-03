package com.example.river.imagelist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = Adapter(this, getDataList())
        recyclerView.adapter = adapter
    }

    fun getDataList(): MutableList<ImageModel> {
        val list = arrayListOf<ImageModel>()
        for (i in 0..5) {
            val str = "img${String.format("%02d", i+1)}"
            list.add(ImageModel(findResourceIdByString(str), str))
        }
        return list
    }

    fun findResourceIdByString(str: String): Int {
        return this.resources.getIdentifier(str, "drawable", this.packageName)
    }
}
