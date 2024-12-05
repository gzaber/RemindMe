package com.gzaber.remindme.data.source

import com.gzaber.remindme.shared.toMilliseconds
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Test
import kotlin.test.assertEquals

class ConvertersTest {

    private val date = LocalDate(year = 2024, month = Month.DECEMBER, dayOfMonth = 6)
    private val time = LocalTime(hour = 13, minute = 30)
    private val localDateTime = LocalDateTime(date, time)
    private val dateTimeMillis = localDateTime.toMilliseconds()

    @Test
    fun fromTimestamp_convertsToLocalDateTime() {
        val result = Converters().fromTimestamp(dateTimeMillis)

        assertEquals(localDateTime, result)
    }

    @Test
    fun toTimestamp_convertsToMilliseconds() {
        val result = Converters().toTimestamp(localDateTime)

        assertEquals(dateTimeMillis, result)
    }
}