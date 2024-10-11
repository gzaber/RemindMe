package com.gzaber.remindme.ui.reminders

import com.gzaber.remindme.ui.reminders.model.UiReminder

data class RemindersUiState(
    val reminders: List<UiReminder> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)