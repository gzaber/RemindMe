package com.gzaber.remindme.shared

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

fun LocalDateTime.toMilliseconds(): Long {
    val timeZone = TimeZone.currentSystemDefault()
    return this.toInstant(timeZone).toEpochMilliseconds()
}

fun LocalDateTime.minus(other: LocalDateTime): Duration {
    val timeZone = TimeZone.currentSystemDefault()
    return this.toInstant(timeZone).minus(other.toInstant(timeZone))
}

fun LocalDateTime.minus(period: DateTimePeriod): LocalDateTime {
    val timeZone = TimeZone.currentSystemDefault()
    return this.toInstant(timeZone).minus(period, timeZone).toLocalDateTime(timeZone)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Clock.Companion.atPresent(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalDateTime.format(): String {
    return this.format(
        LocalDateTime.Format {
            byUnicodePattern("yyyy-MM-dd HH:mm")
        })
}