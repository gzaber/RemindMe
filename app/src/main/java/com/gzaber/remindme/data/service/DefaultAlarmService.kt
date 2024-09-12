package com.gzaber.remindme.data.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class DefaultAlarmService(
    private val alarmManager: AlarmManager,
    private val context: Context
) : AlarmService {

    override fun schedule(requestCode: Int, title: String, content: String, dateTimeMillis: Long) {
        val alarmIntent = createPendingIntent(requestCode, title, content)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            dateTimeMillis,
            alarmIntent
        )
    }

    override fun delete(requestCode: Int) {
        val alarmIntent = createPendingIntent(requestCode = requestCode)
        alarmManager.cancel(alarmIntent)
    }

    private fun createPendingIntent(
        requestCode: Int,
        title: String = "",
        content: String = ""
    ): PendingIntent {
        return Intent(context, AlarmReceiver::class.java)
            .putExtra(AlarmService.REQUEST_CODE_KEY, requestCode)
            .putExtra(AlarmService.TITLE_KEY, title)
            .putExtra(AlarmService.CONTENT_KEY, content)
            .let { intent ->
                PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }
    }
}