package com.example.river.gtihubstars

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.info.*

class InfoActivity : AppCompatActivity {
    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.info)

        recyclerView.layoutManager = LinearLayoutManager(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val projects: ArrayList<ProjectModel> =
            intent.extras.getParcelableArrayList("projects")
        val adapter = CustomAdapter(this, projects)
        recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}