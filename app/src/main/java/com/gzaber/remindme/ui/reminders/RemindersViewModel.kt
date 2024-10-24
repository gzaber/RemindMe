package com.gzaber.remindme.ui.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.shared.atPresent
import com.gzaber.remindme.shared.format
import com.gzaber.remindme.shared.minus
import com.gzaber.remindme.ui.reminders.model.ExpirationStatus
import com.gzaber.remindme.ui.reminders.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime

class RemindersViewModel(
    private val remindersRepository: RemindersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemindersUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        observeReminders()
    }

    fun toggleShowDeleteDialog() {
        _uiState.update {
            it.copy(showDeleteDialog = !_uiState.value.showDeleteDialog)
        }
    }

    fun onReminderIdChanged(id: Int) {
        _uiState.update {
            it.copy(reminderIdToDelete = id)
        }
    }

    fun deleteReminder() {
        viewModelScope.launch {
            try {
                remindersRepository.delete(_uiState.value.reminderIdToDelete)
            } catch (e: Throwable) {
                _uiState.update {
                    it.copy(isError = true)
                }
            }
        }
    }

    private fun observeReminders() {
        viewModelScope.launch {
            remindersRepository.observeAll()
                .catch { _ ->
                    _uiState.update {
                        it.copy(isError = true)
                    }
                }
                .collect { reminders ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            reminders = reminders
                                .sortedBy { reminder -> reminder.expiration }
                                .map { reminder ->
                                    reminder.toUiModel(
                                        formattedExpiration = reminder.expiration.format(),
                                        expirationStatus = calculateExpirationStatus(reminder.expiration)
                                    )
                                }
                        )
                    }
                }
        }
    }

    private fun calculateExpirationStatus(expirationDateTime: LocalDateTime): ExpirationStatus {
        val now = Clock.atPresent()
        val durationDifference = expirationDateTime.minus(now)
        val expirationStatus = when {
            durationDifference.inWholeSeconds < 0 -> ExpirationStatus.EXPIRED
            durationDifference.inWholeDays == 0L -> ExpirationStatus.WITHIN_DAY
            durationDifference.inWholeDays in 1..<7 -> ExpirationStatus.WITHIN_WEEK
            else -> ExpirationStatus.MORE
        }

        return expirationStatus
    }
}