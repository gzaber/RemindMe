package com.gzaber.remindme.ui.reminders.model

import com.gzaber.remindme.data.repository.model.Reminder

fun Reminder.toUiModel(
    formattedExpiration: String,
    expirationStatus: ExpirationStatus
): UiReminder = UiReminder(
    id = id,
    name = name,
    formattedExpiration = formattedExpiration,
    expirationStatus = expirationStatus
)
