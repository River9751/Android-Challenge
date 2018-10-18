package com.example.river.pushmessaging

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CusMessagingService : FirebaseMessagingService() {

    private val broadcastManager: LocalBroadcastManager = LocalBroadcastManager.getInstance(this)

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val intent = Intent("CusMessage")

        intent.putExtra("from", remoteMessage?.from.toString())

        if (remoteMessage?.notification != null) {
            intent.putExtra("body", remoteMessage.notification?.body)
        }

        broadcastManager.sendBroadcast(intent)
    }
}