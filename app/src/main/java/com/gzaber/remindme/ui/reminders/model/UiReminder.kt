package com.gzaber.remindme.ui.reminders.model

import androidx.compose.ui.graphics.Color

data class UiReminder(
    val id: Int,
    val name: String,
    val color: Color,
    val expiration: String
)
