package com.gzaber.remindme.ui.reminders

import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.helper.MainDispatcherRule
import com.gzaber.remindme.helper.plus
import com.gzaber.remindme.shared.atPresent
import com.gzaber.remindme.shared.minus
import com.gzaber.remindme.ui.reminders.model.ExpirationStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemindersViewModelTest {
    private lateinit var repository: RemindersRepository

    private val now = Clock.atPresent()
    private val reminder1 = Reminder(id = 1, "name1", now.minus(DateTimePeriod(days = 1)), now)
    private val reminder2 = Reminder(id = 2, "name2", now.plus(DateTimePeriod(hours = 6)), now)
    private val reminder3 = Reminder(id = 3, "name3", now.plus(DateTimePeriod(days = 4)), now)
    private val reminder4 = Reminder(id = 4, "name4", now.plus(DateTimePeriod(days = 15)), now)
    private val reminders = listOf(reminder1, reminder2, reminder3, reminder4)

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = mockk<RemindersRepository>()
        every { repository.observeAll() } returns
                flow { emit(reminders) }
    }

    @Test
    fun init_successfullySubscribesToRemindersRepositoryFlow_emitsStateWithRemindersList() =
        runTest {
            val state = RemindersViewModel(repository).uiState.value

            assertFalse(state.isLoading)
            assertFalse(state.isError)
            assert(state.reminders.size == 4)
            verify(exactly = 1) { repository.observeAll() }
        }

    @Test
    fun init_remindersRepositoryFlowThrowsException_emitsStateWithErrorStatus() = runTest {
        every { repository.observeAll() } returns
                flow { emit(reminders) }.onStart { throw Exception() }
        val state = RemindersViewModel(repository).uiState.value

        assertTrue(state.isLoading)
        assertTrue(state.isError)
        assertTrue(state.reminders.isEmpty())
        verify(exactly = 1) { repository.observeAll() }
    }

    @Test
    fun toggleShowDeleteDialog_emitsStateWithToggledValue() = runTest {
        val viewModel = RemindersViewModel(repository)

        assertFalse(viewModel.uiState.value.showDeleteDialog)
        viewModel.toggleShowDeleteDialog()
        assertTrue(viewModel.uiState.value.showDeleteDialog)
    }

    @Test
    fun onReminderIdChanged_emitsStateWithUpdatedValue() = runTest {
        val viewModel = RemindersViewModel(repository)

        assertEquals(0, viewModel.uiState.value.reminderIdToDelete)
        viewModel.onReminderIdChanged(2)
        assertEquals(2, viewModel.uiState.value.reminderIdToDelete)
    }

    @Test
    fun deleteReminder_repositorySuccessfullyDeletes() = runTest {
        coEvery { repository.delete(any()) } returns Unit

        val viewModel = RemindersViewModel(repository)
        viewModel.onReminderIdChanged(1)
        viewModel.deleteReminder()

        assertFalse(viewModel.uiState.value.isError)
        coVerify(exactly = 1) { repository.delete(1) }
    }

    @Test
    fun deleteReminder_repositoryThrowsException_emitsStateWithErrorStatus() = runTest {
        coEvery { repository.delete(any()) } throws Exception()

        val viewModel = RemindersViewModel(repository)
        viewModel.onReminderIdChanged(1)
        viewModel.deleteReminder()

        assertTrue(viewModel.uiState.value.isError)
        coVerify(exactly = 1) { repository.delete(1) }
    }

    @Test
    fun calculateExpirationStatus_correctlyCalculatesExpirationStatus() = runTest {
        val state = RemindersViewModel(repository).uiState.value

        assertEquals(ExpirationStatus.EXPIRED, state.reminders[0].expirationStatus)
        assertEquals(ExpirationStatus.WITHIN_DAY, state.reminders[1].expirationStatus)
        assertEquals(ExpirationStatus.WITHIN_WEEK, state.reminders[2].expirationStatus)
        assertEquals(ExpirationStatus.MORE, state.reminders[3].expirationStatus)
    }
}