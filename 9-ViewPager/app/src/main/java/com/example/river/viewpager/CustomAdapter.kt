package com.example.river.viewpager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_layout.view.*

class CustomAdapter : PagerAdapter {

    val context: Context
    val imageList: MutableList<Int>

    constructor(context: Context, imageList: MutableList<Int>) : super() {
        this.context = context
        this.imageList = imageList
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        println("*** isViewFromObject View:${p0.tag} Any:${(p1 as View).tag}")
        return p0 == p1
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(R.layout.image_layout, container, false)
        v.imageView.setImageResource(imageList[position])

        v.tag = "image${position+1}"

        container.addView(v)

        println("*** instantiateItem ${v.tag}")

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        println("*** destroyItem ${(`object` as View).tag}")
        container.removeView(`object` as ConstraintLayout)
    }
}