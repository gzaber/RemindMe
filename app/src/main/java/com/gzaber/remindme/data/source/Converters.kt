package com.gzaber.remindme.data.source

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class Converters {
    private val timeZone = TimeZone.currentSystemDefault()

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(value).toLocalDateTime(timeZone)
    }

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(timeZone).toEpochMilliseconds()
    }
}