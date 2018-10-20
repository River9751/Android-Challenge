package com.example.river.pulltorequest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.info_item.view.*

class CusAdapter : RecyclerView.Adapter<CusAdapter.CusViewHolder> {

    private var list: MutableList<InfoItem>

    constructor(list: MutableList<InfoItem>) : super() {
        this.list = list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CusViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.info_item, p0, false)
        return CusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CusViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.textView.text = list[position].name
            itemView.imageView.setColorFilter(list[position].color)
        }
    }

    fun addListItem(color: Int) {
        var size = list.size + 1
        this.list.add(InfoItem("Android ${size++}", color))
        this.notifyDataSetChanged()
    }
}