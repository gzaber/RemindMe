package com.gzaber.remindme.ui.addedit

import com.gzaber.remindme.shared.atPresent
import com.gzaber.remindme.shared.toLocalDateTime
import com.gzaber.remindme.shared.toMilliseconds
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit

data class AddEditUiState(
    val name: String = "",
    val expirationDateMillis: Long = Clock.atPresent().toMilliseconds(),
    val expirationHour: Int = Clock.atPresent().hour,
    val expirationMinute: Int = Clock.atPresent().minute,
    val advanceValue: Int = 1,
    val advanceUnit: DateTimeUnit = DateTimeUnit.DAY,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val showAdvancePicker: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isError: Boolean = false
) {
    val formattedDate: String
        get() {
            val expirationDate = expirationDateMillis.toLocalDateTime().date
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