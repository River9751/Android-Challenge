package com.example.river.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        button.setOnClickListener {
            //版本 O 以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotificationAboveOreo(pendingIntent)
            } else {
                showNotificationBelowOreo()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotificationAboveOreo(pendingIntent: PendingIntent) {

//        var aaa = BitmapFactory.decodeResource(resources, R.drawable.ic_star_black_24dp)

        val builder =
            NotificationCompat.Builder(this, "channel01")
                .setSmallIcon(R.drawable.ic_star_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(resources ,R.drawable.ic_star_black_24dp))
                .setContentTitle("My notification")
                .setContentText("Hello World")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(300, 600, 300, 600))
                .setLights(Color.GREEN, 1000, 1000)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        val channel = NotificationChannel(
            "channel01",
            "MyChannel",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationId = 1
        val notification = builder.build()
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(notificationId, notification)
    }

    fun showNotificationBelowOreo() {


var bb = BitmapFactory.Options()
//        bb.inBitmap=

        val builder =
            NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_star_black_24dp)
                .setContentTitle("My notification")
                .setContentText("Hello World")
                //.setLargeIcon(BitmapFactory.decodeResource(resources ,R.drawable.ic_star_black_24dp))

        val notificationId = 2
        val notification = builder.build()

        notificationManager.notify(notificationId, notification)
    }
}
