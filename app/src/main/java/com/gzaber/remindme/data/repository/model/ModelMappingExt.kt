package com.gzaber.remindme.data.repository.model

import com.gzaber.remindme.data.source.ReminderEntity

fun Reminder.toEntity() = ReminderEntity(id, name, color, expiration, advance)

fun ReminderEntity.toModel() = Reminder(id, name, color, expiration, advance)