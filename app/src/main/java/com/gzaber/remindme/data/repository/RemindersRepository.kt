package com.gzaber.remindme.data.repository

import com.gzaber.remindme.data.repository.model.Reminder
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {
    suspend fun create(reminder: Reminder)
    suspend fun update(reminder: Reminder)
    suspend fun delete(reminder: Reminder)
    fun observeAll(): Flow<List<Reminder>>
}