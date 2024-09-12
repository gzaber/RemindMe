package com.gzaber.remindme.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver(
    private val notificationService: NotificationService
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra(AlarmService.REQUEST_CODE_KEY, 0) ?: 0
        val title = intent?.getStringExtra(AlarmService.TITLE_KEY) ?: ""
        val content = intent?.getStringExtra(AlarmService.CONTENT_KEY) ?: ""

        notificationService.send(id, title, content)
    }
}