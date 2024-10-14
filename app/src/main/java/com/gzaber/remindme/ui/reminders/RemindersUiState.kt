package com.gzaber.remindme.ui.reminders

import com.gzaber.remindme.ui.reminders.model.UiReminder

data class RemindersUiState(
    val reminders: List<UiReminder> = emptyList(),
    val showDeleteDialog: Boolean = false,
    val reminderIdToDelete: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)