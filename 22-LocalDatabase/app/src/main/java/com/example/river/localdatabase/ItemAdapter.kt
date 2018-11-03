package com.example.river.localdatabase

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter(val ctx: Context, var list: MutableList<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.CusViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CusViewHolder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.item, p0, false)
        return CusViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CusViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.index.text = list[position].id.toString()
            itemView.name.text = list[position].name
        }
    }

    fun updateList(newList: MutableList<ItemModel>) {
        this.list = newList
        this.notifyDataSetChanged()
    }

}