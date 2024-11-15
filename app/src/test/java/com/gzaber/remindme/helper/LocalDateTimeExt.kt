package com.gzaber.remindme.helper

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.plus(period: DateTimePeriod): LocalDateTime {
    val timeZone = TimeZone.currentSystemDefault()
    return this.toInstant(timeZone).plus(period, timeZone).toLocalDateTime(timeZone)
}