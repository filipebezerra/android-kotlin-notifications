package dev.filipebezerra.android.eggtimernotifications.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.filipebezerra.android.eggtimernotifications.util.getNotificationManager
import dev.filipebezerra.android.eggtimernotifications.util.sendNotification
import timber.log.Timber

class EggTimerFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        Timber.i("Refreshed Firebase Token: $newToken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        with(remoteMessage) {
            from?.run { Timber.d("Message received from $this") }
            data.takeIf { it.isNotEmpty() }?.run {
                Timber.d("Data received within the message $data")
            }
            notification?.body?.run { applicationContext.getNotificationManager().sendNotification(
                this,
                applicationContext
            ) }
        }
    }
}