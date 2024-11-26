package com.gzaber.remindme.ui.addedit

import com.gzaber.remindme.shared.toMilliseconds
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Assert.assertEquals
import org.junit.Test

class AddEditUiStateTest {
    private val date = LocalDate(year = 2024, month = Month.DECEMBER, dayOfMonth = 6)
    private val dateTime = LocalDateTime(date, LocalTime(0, 0))

    private fun createAddEditUiState(
        hour: Int = 0,
        minute: Int = 0,
        advanceValue: Int = 1
    ): AddEditUiState {
        return AddEditUiState(
            expirationDateMillis = dateTime.toMilliseconds(),
            expirationHour = hour,
            expirationMinute = minute,
            advanceValue = advanceValue
        )
    }

    @Test
    fun formattedDate_returnsDateInAppropriateFormat() {
        assertEquals("6 December 2024", createAddEditUiState().formattedDate)
    }

    @Test
    fun formattedTime_oneDigitHourAndMinute_returnsTimeInAppropriateFormat() {
        assertEquals("01:05", createAddEditUiState(1, 5).formattedTime)
    }

    @Test
    fun formattedTime_twoDigitHourAndMinute_returnsTimeInAppropriateFormat() {
        assertEquals("14:15", createAddEditUiState(14, 15).formattedTime)
    }

    @Test
    fun formattedAdvance_singular_returnsAdvanceInAppropriateFormat() {
        assertEquals("1 day", createAddEditUiState().formattedAdvance)
    }

    @Test
    fun formattedAdvance_plural_returnsAdvanceInAppropriateFormat() {
        assertEquals("2 days", createAddEditUiState(advanceValue = 2).formattedAdvance)
    }
}