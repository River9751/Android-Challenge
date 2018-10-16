package com.example.river.ball

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.animate_item.view.*

class CusAdapter : RecyclerView.Adapter<CusAdapter.CusViewHolder> {

    var list: MutableList<AnimateItem>
    val context: Context
    val click: (position: Int) -> Unit

    constructor(
            context: Context,
            list: MutableList<AnimateItem>,
            click: (position: Int) -> Unit) : super() {
        this.list = list
        this.context = context
        this.click = click
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CusViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.animate_item, p0, false)
        return CusViewHolder(v)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: CusViewHolder, position: Int) {
        holder.itemView.setOnClickListener { click(position) }
        holder.bind(position, list[position].title)
    }

    inner class CusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, title: String) {
            itemView.position.text = position.toString()
            itemView.title.text = title
        }
    }
}