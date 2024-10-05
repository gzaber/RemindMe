package com.gzaber.remindme.data.repository

import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.data.repository.model.toEntity
import com.gzaber.remindme.data.repository.model.toModel
import com.gzaber.remindme.data.service.AlarmService
import com.gzaber.remindme.data.source.RemindersDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant

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
    }

    override suspend fun read(id: Int) = remindersDataSource.read(id).toModel()

    override fun observeAll(): Flow<List<Reminder>> =
        remindersDataSource.observeAll().map { entity ->
            entity.map {
                it.toModel()
            }
        }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun setReminders(reminder: Reminder, id: Int? = null) {
        val timeZone = TimeZone.currentSystemDefault()
        val expirationMillis = reminder.expiration.toInstant(timeZone).toEpochMilliseconds()
        val advanceMillis = reminder.advance.toInstant(timeZone).toEpochMilliseconds()
        val formattedExpiration = reminder.expiration.format(
            LocalDateTime.Format {
                byUnicodePattern("yyyy-MM-dd HH:mm")
            })

        alarmService.schedule(
            id ?: reminder.id,
            reminder.name,
            formattedExpiration,
            expirationMillis
        )
        alarmService.schedule(
            id?.unaryMinus() ?: reminder.id,
            reminder.name,
            formattedExpiration,
            advanceMillis
        )
    }
}