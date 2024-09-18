package com.gzaber.remindme.data.repository.model

import com.gzaber.remindme.data.source.ReminderEntity

fun Reminder.toEntity() = ReminderEntity(id, name, expiration, advance)

fun ReminderEntity.toModel() = Reminder(id, name, expiration, advance)