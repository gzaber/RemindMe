package com.gzaber.remindme.ui.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.ui.reminders.model.ExpirationStatus
import com.gzaber.remindme.ui.reminders.model.UiReminder
import com.gzaber.remindme.ui.reminders.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

data class RemindersUiState(
    val reminders: List<UiReminder> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

class RemindersViewModel(
    private val remindersRepository: RemindersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemindersUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
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
                            reminders = reminders.map { reminder ->
                                reminder.toUiModel(
                                    formattedExpiration = formatExpiration(reminder.expiration),
                                    expirationStatus = calculateExpirationStatus(reminder.expiration)
                                )
                            }
                        )
                    }
                }
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch {
            try {
                remindersRepository.delete(id)
            } catch (e: Throwable) {
                _uiState.update {
                    it.copy(isError = true)
                }
            }
        }
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun formatExpiration(expirationDateTime: LocalDateTime): String =
        expirationDateTime.format(
            LocalDateTime.Format {
                byUnicodePattern("yyyy-MM-dd HH:mm")
            })

    private fun calculateExpirationStatus(expirationDateTime: LocalDateTime): ExpirationStatus {
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(timeZone)
        val durationDifference = expirationDateTime.toInstant(timeZone)
            .minus(today.toInstant(timeZone))
        val expirationStatus = when {
            durationDifference.inWholeSeconds < 0 -> ExpirationStatus.EXPIRED
            durationDifference.inWholeDays in 0..1 -> ExpirationStatus.WITHIN_DAY
            durationDifference.inWholeDays in 2..7 -> ExpirationStatus.WITHIN_WEEK
            else -> ExpirationStatus.MORE
        }

        return expirationStatus
    }
}