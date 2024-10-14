package com.gzaber.remindme.data.source

import androidx.room.TypeConverter
import com.gzaber.remindme.shared.toLocalDateTime
import com.gzaber.remindme.shared.toMilliseconds
import kotlinx.datetime.LocalDateTime

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return value.toLocalDateTime()
    }

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime): Long {
        return dateTime.toMilliseconds()
    }
}