package com.example.river.pushmessaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AlertDialog

class CusBroadcastReceiver(val ctx: Context) : BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        showNotification()
        showDialog(intent)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification() {
        val builder =
            NotificationCompat.Builder(ctx, "channel01")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("My notification")
                .setContentText("Hello World")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(300, 600, 300, 600))
                .setLights(Color.GREEN, 1000, 1000)

        val channel = NotificationChannel(
            "channel01",
            "MyChannel",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationId = 1
        val notification = builder.build()
        notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(notificationId, notification)
    }

    fun showDialog(intent: Intent?) {
        AlertDialog.Builder(ctx)
            .setTitle(intent?.getStringExtra("from"))
            .setMessage(intent?.getStringExtra("body"))
            .show()
    }
}