package com.gzaber.remindme.data.repository

import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.data.repository.model.toEntity
import com.gzaber.remindme.data.repository.model.toModel
import com.gzaber.remindme.data.service.AlarmService
import com.gzaber.remindme.data.source.RemindersDao
import com.gzaber.remindme.shared.format
import com.gzaber.remindme.shared.toMilliseconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultRemindersRepository(
    private val remindersDataSource: RemindersDao,
    private val alarmService: AlarmService
) : RemindersRepository {

    override suspend fun create(reminder: Reminder) {
        val id = remindersDataSource.create(reminder.toEntity())
        setReminders(reminder, id.toInt())
    }

    override suspend fun update(reminder: Reminder) {
        remindersDataSource.update(reminder.toEntity())
        setReminders(reminder)
    }

    override suspend fun delete(id: Int) {
        remindersDataSource.delete(id)
        alarmService.delete(id)
        alarmService.delete(id.unaryMinus())
    }

    override suspend fun read(id: Int) = remindersDataSource.read(id).toModel()

    override fun observeAll(): Flow<List<Reminder>> =
        remindersDataSource.observeAll().map { entity ->
            entity.map {
                it.toModel()
            }
        }

    private fun setReminders(reminder: Reminder, id: Int? = null) {
        val expirationMillis = reminder.expiration.toMilliseconds()
        val advanceMillis = reminder.advance.toMilliseconds()
        val formattedExpiration = reminder.expiration.format()

        alarmService.schedule(
            id ?: reminder.id,
            reminder.name,
            formattedExpiration,
            expirationMillis
        )
        alarmService.schedule(
            (id ?: reminder.id).unaryMinus(),
            reminder.name,
            formattedExpiration,
            advanceMillis
        )
    }
}