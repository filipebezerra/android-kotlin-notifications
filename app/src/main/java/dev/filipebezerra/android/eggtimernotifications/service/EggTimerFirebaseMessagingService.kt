package dev.filipebezerra.android.eggtimernotifications.service

import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class EggTimerFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        Timber.i("Refreshed Firebase Token: $newToken")
    }
}