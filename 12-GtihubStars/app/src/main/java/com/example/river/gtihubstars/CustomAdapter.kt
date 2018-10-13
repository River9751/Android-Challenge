package com.example.river.gtihubstars

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    var projectList: MutableList<ProjectModel>
    var context: Context

    constructor(context: Context, projectList: MutableList<ProjectModel>) : super() {
        this.projectList = projectList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(this.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.projectList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(projectList[p1])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(model: ProjectModel) {
            view.projectTextView.text = model.projectName
            view.descriptionTextView.text = model.description
            view.starTextView.text = model.starCount.toString()
            view.forkTextView.text = model.forkCount.toString()
            view.usernameTextView.text = model.username
        }
    }
}