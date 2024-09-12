package com.gzaber.remindme.data.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class DefaultNotificationService(
    private val notificationManager: NotificationManager,
    private val context: Context
) : NotificationService {

    override fun send(id: Int, title: String, content: String) {
        val notification = NotificationCompat.Builder(context, NotificationService.CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .build()

        notificationManager.notify(id, notification)
    }
}