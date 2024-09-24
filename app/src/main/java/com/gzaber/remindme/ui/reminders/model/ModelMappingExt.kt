package com.gzaber.remindme.ui.reminders.model

import androidx.compose.ui.graphics.Color
import com.gzaber.remindme.data.repository.model.Reminder
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

@OptIn(FormatStringsInDatetimeFormats::class)
fun Reminder.toUiModel(): UiReminder {
    val formattedExpiration = this.expiration.format(
        LocalDateTime.Format {
            byUnicodePattern("yyyy-MM-dd HH:mm")
        })
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val color = when (today.daysUntil(this.expiration.date)) {
        in Int.MIN_VALUE..-1 -> Color.LightGray
        0, 1 -> Color.Red
        in 2..7 -> Color.Yellow
        else -> Color.Green
    }

    return UiReminder(
        id = id,
        name = name,
        color = color,
        expiration = formattedExpiration
    )
}