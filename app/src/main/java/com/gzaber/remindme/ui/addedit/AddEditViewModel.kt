package com.gzaber.remindme.ui.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gzaber.remindme.AddEdit
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.shared.minus
import com.gzaber.remindme.shared.toLocalDateTime
import com.gzaber.remindme.shared.toMilliseconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.time.DurationUnit

class AddEditViewModel(
    private val remindersRepository: RemindersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _reminderId: Int? = savedStateHandle.toRoute<AddEdit>().id
    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState = _uiState.asStateFlow()
    val advanceUnits = listOf(DateTimeUnit.MINUTE, DateTimeUnit.HOUR, DateTimeUnit.DAY)
    val advanceValues = (1..30).toList()

    init {
        if (_reminderId != null) readReminder(_reminderId)
    }

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun onDateChanged(dateMillis: Long?) {
        if (dateMillis != null) {
            _uiState.update {
                it.copy(expirationDateMillis = dateMillis)
            }
        }

    }

    fun onTimeChanged(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(expirationHour = hour, expirationMinute = minute)
        }
    }

    fun onAdvanceUnitChanged(unitIndex: Int) {
        _uiState.update {
            it.copy(advanceUnit = advanceUnits[unitIndex])
        }
    }

    fun onAdvanceValueChanged(value: Int) {
        _uiState.update {
            it.copy(advanceValue = value)
        }
    }

    fun toggleShowingDatePicker() {
        _uiState.update {
            it.copy(showDatePicker = !_uiState.value.showDatePicker)
        }
    }

    fun toggleShowingTimePicker() {
        _uiState.update {
            it.copy(showTimePicker = !_uiState.value.showTimePicker)
        }
    }

    fun toggleShowingAdvancePicker() {
        _uiState.update {
            it.copy(showAdvancePicker = !_uiState.value.showAdvancePicker)
        }
    }

    fun saveReminder() {
        _uiState.update { it.copy(isLoading = true) }
        val expirationDate = _uiState.value.expirationDateMillis.toLocalDateTime().date
        val expirationTime = LocalTime(
            hour = _uiState.value.expirationHour,
            minute = _uiState.value.expirationMinute
        )
        val expirationDateTime = LocalDateTime(expirationDate, expirationTime)
        val advanceDateTimePeriod = DateTimePeriod(
            days = if (_uiState.value.advanceUnit == DateTimeUnit.DAY) _uiState.value.advanceValue else 0,
            hours = if (_uiState.value.advanceUnit == DateTimeUnit.HOUR) _uiState.value.advanceValue else 0,
            minutes = if (_uiState.value.advanceUnit == DateTimeUnit.MINUTE) _uiState.value.advanceValue else 0,
        )
        val advanceDateTime = expirationDateTime.minus(advanceDateTimePeriod)

        val reminder = Reminder(
            name = _uiState.value.name,
            expiration = expirationDateTime,
            advance = advanceDateTime
        )

        viewModelScope.launch {
            try {
                if (_reminderId == null) {
                    remindersRepository.create(reminder)
                } else {
                    remindersRepository.update(reminder.copy(id = _reminderId))
                }
                _uiState.update { it.copy(isSaved = true) }
            } catch (e: Throwable) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    private fun readReminder(id: Int) {
        viewModelScope.launch {
            try {
                remindersRepository.read(id).let { reminder ->
                    _uiState.update {
                        val advance =
                            calculateAdvanceValueAndUnit(reminder.expiration, reminder.advance)

                        it.copy(
                            name = reminder.name,
                            expirationDateMillis = reminder.expiration.toMilliseconds(),
                            expirationHour = reminder.expiration.hour,
                            expirationMinute = reminder.expiration.minute,
                            advanceValue = advance.first,
                            advanceUnit = advance.second,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Throwable) {
                _uiState.update { it.copy(isError = true) }
            }
        }
    }

    private fun calculateAdvanceValueAndUnit(
        expiration: LocalDateTime,
        advance: LocalDateTime
    ): Pair<Int, DateTimeUnit> {
        val durationDifference = expiration.minus(advance)
        val advancePairs = listOf(
            Pair(durationDifference.inWholeMinutes, DurationUnit.MINUTES),
            Pair(durationDifference.inWholeHours, DurationUnit.HOURS),
            Pair(durationDifference.inWholeDays, DurationUnit.DAYS)
        )
        val min = advancePairs.filter { it.first > 0 }.minBy { it.first }
        val advanceValue = min.first.toInt()
        val advanceUnit = when (min.second) {
            DurationUnit.MINUTES -> DateTimeUnit.MINUTE
            DurationUnit.HOURS -> DateTimeUnit.HOUR
            else -> DateTimeUnit.DAY
        }

        return Pair(advanceValue, advanceUnit)
    }
}