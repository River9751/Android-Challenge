package com.example.river.sidemenu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager = supportFragmentManager
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener {
//            Toast.makeText(this, it.itemId.toString(), Toast.LENGTH_LONG).show()
            when (it.itemId) {
                R.id.item01 -> {
                    changeFragment(it.title.toString(), Item1Fragment())
                    drawer.closeDrawers()
                }
                R.id.group1_1 -> {
                    changeFragment(it.title.toString(), Group1_1Fragment())
                    drawer.closeDrawers()
                }
            }
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> drawer.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


    fun changeFragment(title: String, fragment: Fragment) {
        var bundle = Bundle()
        bundle.putString("MainActivity", title)
        fragment.arguments = bundle
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}