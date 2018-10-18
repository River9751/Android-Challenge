package com.example.river.pushmessaging

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var localBroadcastManager: LocalBroadcastManager
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        broadcastReceiver = CusBroadcastReceiver(this)
    }

    override fun onResume() {
        localBroadcastManager.registerReceiver(broadcastReceiver, IntentFilter("CusMessage"))
        super.onResume()
    }

    override fun onPause() {
        localBroadcastManager.unregisterReceiver(broadcastReceiver)
        super.onPause()
    }
}
