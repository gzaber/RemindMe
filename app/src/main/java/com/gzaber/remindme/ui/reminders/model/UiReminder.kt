package com.gzaber.remindme.ui.reminders.model

enum class ExpirationStatus {
    EXPIRED, WITHIN_DAY, WITHIN_WEEK, MORE
}

data class UiReminder(
    val id: Int,
    val name: String,
    val formattedExpiration: String,
    val expirationStatus: ExpirationStatus
)
