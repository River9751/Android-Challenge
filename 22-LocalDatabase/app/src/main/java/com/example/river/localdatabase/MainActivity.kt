package com.example.river.localdatabase

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var helper: CusSQLiteOpenHelper
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helper = CusSQLiteOpenHelper(this, ::refreshView)
        adapter = ItemAdapter(this, mutableListOf())
        refreshView()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.addUser) {
            addUser()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun refreshView() {
        adapter.updateList(helper.getUsers())
        println("*** Refresh")
    }

    private fun addUser() {

        val editText = EditText(this)

        val builder = AlertDialog.Builder(this)
            .setTitle("Add User")
            .setMessage("Enter your name:")
            .setView(editText)
            .setPositiveButton("Add") { dialog, id ->
                helper.addUser("${editText.text}")
            }

        builder.show()
    }
}
