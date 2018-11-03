package com.example.river.layoutswitch

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_big.view.*
import kotlinx.android.synthetic.main.item_small.view.*

class CusAdapter(
    private val ctx: Context,
    private var list: MutableList<ItemModel>,
    private var gridLayoutManager: GridLayoutManager
) : RecyclerView.Adapter<CusAdapter.CusViewHolder>() {

    private val smallItem = 1
    private val bigItem = 2

    override fun getItemViewType(position: Int): Int {
        return when {
            gridLayoutManager.spanCount == 1 -> bigItem
            gridLayoutManager.spanCount == 2 -> smallItem
            else -> 0
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CusViewHolder {

        val v: View = when (viewType) {
            1 -> LayoutInflater.from(ctx).inflate(R.layout.item_small, p0, false)
            2 -> LayoutInflater.from(ctx).inflate(R.layout.item_big, p0, false)
            else -> LayoutInflater.from(ctx).inflate(R.layout.item_small, p0, false)
        }

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
            when (itemViewType) {
                1 -> {
                    itemView.smallNameTextView.text = list[position].name
                }
                2 -> {
                    itemView.bigNameTextView.text = list[position].name
                    itemView.likeTextView.text = "Likes: ${list[position].likeCount}"
                    itemView.commentTextView.text = "Comments: ${list[position].commentCount}"
                }
            }
        }
    }
}