package com.example.river.bottomnavigation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    enum class FragmentType {
        Home,
        Notifications,
        Dashboard
    }

    var type = FragmentType.Home
    val manager = this.supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    type = FragmentType.Home
                }
                R.id.navigation_notifications -> {
                    type = FragmentType.Notifications
                }
                R.id.navigation_dashboard -> {
                    type = FragmentType.Dashboard
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }

            changeScenario()
            true
        }

        changeScenario()
    }

    fun changeScenario() {
        val transaction = manager.beginTransaction()
        when (type) {
            FragmentType.Home -> {
                title = "Home"
                val homeFragment = HomeFragment()
                transaction.replace(R.id.container, homeFragment)
            }

            FragmentType.Dashboard -> {
                val dashboardFragment = DashboardFragment()
                transaction.replace(R.id.container, dashboardFragment)
            }

            FragmentType.Notifications -> {
                val notificationFragment = NotificationFragment()
                transaction.replace(R.id.container, notificationFragment)
            }

        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
