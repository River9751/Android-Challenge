package com.example.river.imagelist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder> {

    var context: Context
    var dataList: MutableList<ImageModel>

    constructor(context: Context, dataList: MutableList<ImageModel>) : super() {
        this.context = context
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageResource(dataList[position].imgId)
        holder.memo.text = dataList[position].memo
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var memo = view.memo
        var imageView = view.imageView
    }
}