package com.gzaber.remindme.ui.reminders.model

import androidx.compose.ui.graphics.Color
import com.gzaber.remindme.data.repository.model.Reminder
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
fun Reminder.toUiModel(): UiReminder {
    val formattedExpiration = this.expiration.format(
        LocalDateTime.Format {
            byUnicodePattern("yyyy-MM-dd HH:mm")
        })

    return UiReminder(
        id = id,
        name = name,
        color = Color(color),
        expiration = formattedExpiration
    )
}