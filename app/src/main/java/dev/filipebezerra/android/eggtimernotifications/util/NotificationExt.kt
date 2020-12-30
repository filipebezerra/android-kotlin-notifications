/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.filipebezerra.android.eggtimernotifications.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.filipebezerra.android.eggtimernotifications.MainActivity
import dev.filipebezerra.android.eggtimernotifications.R
import dev.filipebezerra.android.eggtimernotifications.important.ImportantActivity
import dev.filipebezerra.android.eggtimernotifications.receiver.SnoozeReceiver

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0

/**
 * Builds and delivers the notification.
 *
 * @param context activity context.
 */
fun NotificationManager.sendNotification(
    messageBody: String,
    context: Context,
) {
    val contentIntent = Intent(context, MainActivity::class.java)
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
    val contentPendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val snoozeIntent = Intent(context, SnoozeReceiver::class.java)
    val snoozePendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE,
        snoozeIntent,
        PendingIntent.FLAG_ONE_SHOT
    )

    val eggImage = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.cooked_egg
    )

    val style = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)

    val fullScreenIntent = Intent(context, ImportantActivity::class.java)
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
    val fullScreenPedingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        fullScreenIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    NotificationCompat.Builder(
        context,
        context.getString(R.string.egg_notification_channel_id)
    )
        .setSmallIcon(R.drawable.egg_icon)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        // auto dissmiss notification from status bar
        .setAutoCancel(true)
        // style notifications with Big Style containing a big image
        .setStyle(style)
        // use this to show a small icon when collapsing the notification
        .setLargeIcon(eggImage)
        // snooze action to reeschedule the notification
        .addAction(
            R.drawable.egg_icon,
            context.getString(R.string.snooze),
            snoozePendingIntent
        )
        // priority defines the level of user interruption
            // High priority makes a sound and appears as a heads up notification
            // Default priority makes a sound
            // Low priority makes no sound
            // Min priority makes no sound and does not appear in the status bar
        .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
        // This information about your notification category is used by the system to make decisions about displaying your notification when the device is in Do Not Disturb mode.
        // https://developer.android.com/training/notify-user/build-notification#system-category
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        // https://developer.android.com/training/notify-user/build-notification#urgent-message
        .setFullScreenIntent(fullScreenPedingIntent, true)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .run {
            notify(NOTIFICATION_ID, this.build())
        }
}

fun NotificationManager.createChannel(
    channelId: String,
    channelName: String,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = "Time for breakfast"
            setShowBadge(false)
        }.run {
            createNotificationChannel(this)
        }
    }
}

fun NotificationManager.cancelNotifications() = cancelAll()
