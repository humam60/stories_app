package com.humam.my_stories

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class not_rec: FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("hoi", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("kop", "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                Log.d("kop", "Message data payload: ${remoteMessage.data}")
                //Toast.makeText(this,"hi there ",Toast.LENGTH_SHORT).show

            } else {
                // Handle message within 10 seconds
                Log.d("kop", "Message data payload: ${remoteMessage.data}")
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("koko", "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}