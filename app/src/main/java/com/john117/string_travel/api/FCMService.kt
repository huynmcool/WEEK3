package com.john117.string_travel.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.john117.string_travel.ui.main.MainActivity


class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

        Log.d("Tag", "Receive message=${p0.notification?.title}")

    }

    override fun onNewToken(token: String) {
        Log.d( "MyFirebaseMsgService", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("MyFirebaseMsgService", "sendRegistrationTokenToServer($token)")
    }

}