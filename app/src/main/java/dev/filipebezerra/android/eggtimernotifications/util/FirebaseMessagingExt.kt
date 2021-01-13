package dev.filipebezerra.android.eggtimernotifications.util

import android.content.Context
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import dev.filipebezerra.android.eggtimernotifications.R

private const val BREAKFAST_TOPIC = "breakfast"

fun FirebaseMessaging.subscribeToTopicBreakfast(context: Context) {
    subscribeToTopic(BREAKFAST_TOPIC)
        .addOnCompleteListener {
            val userFeedback = if (it.isSuccessful)
                R.string.message_subscribed
            else
                R.string.message_subscribe_failed
            Toast.makeText(context.applicationContext, userFeedback, Toast.LENGTH_SHORT).show();
        }
}