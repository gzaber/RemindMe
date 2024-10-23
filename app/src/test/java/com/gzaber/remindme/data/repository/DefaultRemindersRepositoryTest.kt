package com.gzaber.remindme.data.repository

import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.data.repository.model.toEntity
import com.gzaber.remindme.data.service.AlarmService
import com.gzaber.remindme.data.source.RemindersDao
import com.gzaber.remindme.shared.format
import com.gzaber.remindme.shared.toMilliseconds
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultRemindersRepositoryTest {

    private lateinit var dataSource: RemindersDao
    private lateinit var alarmService: AlarmService
    private lateinit var repository: DefaultRemindersRepository

    private val date = LocalDate(year = 2024, month = Month.DECEMBER, dayOfMonth = 6)
    private val expirationDateTime = LocalDateTime(date, LocalTime(hour = 12, minute = 0))
    private val advanceDateTime = LocalDateTime(date, LocalTime(hour = 10, minute = 0))

    @Before
    fun setUp() {
        dataSource = mockk<RemindersDao>()
        alarmService = mockk<AlarmService>()
        repository = DefaultRemindersRepository(dataSource, alarmService)

        every { alarmService.schedule(any(), any(), any(), any()) } returns Unit
        every { alarmService.delete(any()) } returns Unit
    }

    @Test
    fun create_savesReminderToDataSourceAndSetsAlarms() = runTest {
        val id = 1L
        coEvery { dataSource.create(any()) } returns id
        val reminder =
            Reminder(name = "name", expiration = expirationDateTime, advance = advanceDateTime)

        repository.create(reminder)

        coVerify(exactly = 1) { dataSource.create(reminder.toEntity()) }
        verify(exactly = 1) {
            alarmService.schedule(
                id.toInt(),
                reminder.name,
                reminder.expiration.format(),
                reminder.expiration.toMilliseconds()
            )
        }
        verify(exactly = 1) {
            alarmService.schedule(
                -id.toInt(),
                reminder.name,
                reminder.expiration.format(),
                reminder.advance.toMilliseconds()
            )
        }
    }

    @Test
    fun update_updatesReminderFromDataSourceAndAlarms() = runTest {
        coEvery { dataSource.update(any()) } returns Unit
        val id = 2
        val reminder =
            Reminder(
                id = id,
                name = "name",
                expiration = expirationDateTime,
                advance = advanceDateTime
            )

        repository.update(reminder)

        coVerify(exactly = 1) { dataSource.update(reminder.toEntity()) }
        verify(exactly = 1) {
            alarmService.schedule(
                id,
                reminder.name,
                reminder.expiration.format(),
                reminder.expiration.toMilliseconds()
            )
        }
        verify(exactly = 1) {
            alarmService.schedule(
                -id,
                reminder.name,
                reminder.expiration.format(),
                reminder.advance.toMilliseconds()
            )
        }
    }

    @Test
    fun delete_deletesReminderFromDataSourceAndAlarms() = runTest {
        coEvery { dataSource.delete(any()) } returns Unit
        val id = 3

        repository.delete(id)

        coVerify(exactly = 1) { dataSource.delete(id) }
        verify(exactly = 1) {
            alarmService.delete(id)
        }
        verify(exactly = 1) {
            alarmService.delete(-id)
        }
    }

    @Test
    fun read_readsReminderFromDataSource() = runTest {
        val id = 4
        val reminder =
            Reminder(
                id = id,
                name = "name",
                expiration = expirationDateTime,
                advance = advanceDateTime
            )
        coEvery { dataSource.read(any()) } returns reminder.toEntity()

        val result = repository.read(id)

        assertEquals(reminder, result)
        coVerify(exactly = 1) { dataSource.read(id) }
    }

    @Test
    fun observeAll_emitsRemindersFromDataSource() = runTest {
        val reminder1 =
            Reminder(
                id = 1,
                name = "name1",
                expiration = expirationDateTime,
                advance = advanceDateTime
            )
        val reminder2 =
            Reminder(
                id = 2,
                name = "name2",
                expiration = expirationDateTime,
                advance = advanceDateTime
            )
        val reminders = listOf(reminder1, reminder2)
        coEvery { dataSource.observeAll() } returns flow { emit(reminders.map { it.toEntity() }) }

        val result = repository.observeAll().first()

        assertEquals(reminders, result)
        verify(exactly = 1) { dataSource.observeAll() }
    }
}