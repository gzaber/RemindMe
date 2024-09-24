package com.gzaber.remindme.data.repository.model

import kotlinx.datetime.LocalDateTime

data class Reminder(
    val id: Int = 0,
    val name: String,
    val expiration: LocalDateTime,
    val advance: LocalDateTime
)
