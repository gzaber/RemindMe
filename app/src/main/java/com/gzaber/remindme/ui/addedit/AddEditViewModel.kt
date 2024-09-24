package com.gzaber.remindme.ui.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit

data class AddEditUiState(
    val name: String = "",
    val expirationDateMillis: Long = Clock.System.now().toEpochMilliseconds(),
    val expirationHour: Int = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).hour,
    val expirationMinute: Int = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).minute,
    val advanceValue: Int = 1,
    val advanceUnit: DateTimeUnit = DateTimeUnit.DAY,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val showAdvancePicker: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) {
    val formattedDate: String
        get() {
            val expirationDate = Instant.fromEpochMilliseconds(expirationDateMillis)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date
            val formattedMonth =
                expirationDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
            return "${expirationDate.dayOfMonth} $formattedMonth ${expirationDate.year}"
        }

    val formattedTime: String
        get() {
            val formattedHour = if (expirationHour < 10) "0$expirationHour" else "$expirationHour"
            val formattedMinute =
                if (expirationMinute < 10) "0$expirationMinute" else "$expirationMinute"
            return "$formattedHour:$formattedMinute"
        }

    val formattedAdvance: String
        get() {
            val advanceUnitLowercase = advanceUnit.toString().lowercase()
            val suffix = if (advanceValue > 1) "s" else ""
            return "$advanceValue $advanceUnitLowercase$suffix"
        }
}

class AddEditViewModel(
    private val remindersRepository: RemindersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _reminderId: Int? = savedStateHandle["REMINDER_ID"]
    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState = _uiState.asStateFlow()
    val advanceUnits = listOf(DateTimeUnit.MINUTE, DateTimeUnit.HOUR, DateTimeUnit.DAY)
    val advanceValues = (1..30).toList()

    init {
        if (_reminderId != null) {
            readReminder(_reminderId)
        }
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
        _uiState.update {
            it.copy(isLoading = true)
        }
        val expirationDateTime = Instant.fromEpochMilliseconds(_uiState.value.expirationDateMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val reminder = Reminder(
            name = _uiState.value.name,
            expiration = expirationDateTime,
            advance = calculateAdvanceDateTime(
                _uiState.value.advanceValue,
                _uiState.value.advanceUnit,
                _uiState.value.expirationDateMillis
            )
        )

        viewModelScope.launch {
            try {
                if (_reminderId == null) {
                    remindersRepository.create(reminder)
                } else {
                    remindersRepository.update(reminder.copy(id = _reminderId))
                }
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
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                remindersRepository.read(id).let { reminder ->
                    _uiState.update {
                        val advance =
                            calculateAdvanceValueAndUnit(reminder.expiration, reminder.advance)

                        it.copy(
                            name = reminder.name,
                            expirationDateMillis = reminder.expiration.toInstant(TimeZone.currentSystemDefault())
                                .toEpochMilliseconds(),
                            expirationHour = reminder.expiration.hour,
                            expirationMinute = reminder.expiration.minute,
                            advanceValue = advance.first,
                            advanceUnit = advance.second,
                            isLoading = false
                        )
                    }
                }
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

    private fun calculateAdvanceValueAndUnit(
        expiration: LocalDateTime,
        advance: LocalDateTime
    ): Pair<Int, DateTimeUnit> {
        val timeZone = TimeZone.currentSystemDefault()
        val durationDifference = expiration.toInstant(timeZone).minus(advance.toInstant(timeZone))
        val advancePairs = listOf(
            Pair(durationDifference.inWholeMinutes, DurationUnit.MINUTES),
            Pair(durationDifference.inWholeHours, DurationUnit.HOURS),
            Pair(durationDifference.inWholeDays, DurationUnit.DAYS)
        )
        val min = advancePairs.minBy { it.first }
        val advanceValue = min.first.toInt()
        val advanceUnit = when (min.second) {
            DurationUnit.MINUTES -> DateTimeUnit.MINUTE
            DurationUnit.HOURS -> DateTimeUnit.HOUR
            else -> DateTimeUnit.DAY
        }

        return Pair(advanceValue, advanceUnit)
    }

    private fun calculateAdvanceDateTime(
        advanceValue: Int,
        advanceUnit: DateTimeUnit,
        expirationMillis: Long
    ): LocalDateTime {
        val timeZone = TimeZone.currentSystemDefault()
        val advance = Instant.fromEpochMilliseconds(expirationMillis)
            .minus(advanceValue, advanceUnit, timeZone)
            .toLocalDateTime(timeZone)

        return advance
    }


}