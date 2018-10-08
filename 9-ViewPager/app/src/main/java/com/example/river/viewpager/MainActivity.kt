package com.example.river.viewpager

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var imageList: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getImages()
        textView.text = "1 / ${imageList.size}"

        pager.adapter = CustomAdapter(this, imageList)

        pager.addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(p0: Int) {}

                    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                        val currentPosition = p0 + 1
                        textView.text = "$currentPosition / ${imageList.size}"
                    }

                    override fun onPageSelected(p0: Int) {}
                })

    }

    fun getImages() {
        imageList = arrayListOf()
        for (i in R.drawable.img01..R.drawable.img06) {
            imageList.add(i)
            println("*** " + i.toString())
        }
    }
}
