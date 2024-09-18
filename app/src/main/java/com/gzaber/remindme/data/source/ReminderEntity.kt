package com.gzaber.remindme.data.source

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val expiration: LocalDateTime,
    val advance: LocalDateTime
)
