package com.gzaber.remindme.ui.addedit

import androidx.lifecycle.SavedStateHandle
import com.gzaber.remindme.AddEdit
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.helper.MainDispatcherRule
import com.gzaber.remindme.shared.atPresent
import com.gzaber.remindme.shared.minus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddEditViewModelTest {

    private lateinit var repository: RemindersRepository
    private val now = Clock.atPresent()
    private val reminder1 = Reminder(id = 1, "name1", now, now.minus(DateTimePeriod(minutes = 1)))
    private val reminder2 = Reminder(id = 2, "name2", now, now.minus(DateTimePeriod(hours = 1)))
    private val reminder3 = Reminder(id = 3, "name3", now, now.minus(DateTimePeriod(days = 1)))

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = mockk<RemindersRepository>()
    }

    private fun createViewModel(savedStateHandle: SavedStateHandle = SavedStateHandle()): AddEditViewModel {
        return AddEditViewModel(repository, savedStateHandle)
    }

    @Test
    fun init_idArgumentNotPassed_emitsDefaultState() {
        val state = createViewModel().uiState.value

        assertEquals(
            AddEditUiState().copy(
                expirationDateMillis = state.expirationDateMillis,
                expirationHour = state.expirationHour,
                expirationMinute = state.expirationMinute
            ), state
        )
        coVerify(exactly = 0) { repository.read(any()) }
    }

    @Test
    fun init_idArgumentPassed_emitsStateWithReminderReadFromRepository() {
        coEvery { repository.read(any()) } returns reminder1
        val state =
            createViewModel(SavedStateHandle(mapOf(AddEdit.ID_ARG to reminder1.id))).uiState.value

        assertEquals(reminder1.name, state.name)
        coVerify(exactly = 1) { repository.read(reminder1.id) }
    }

    @Test
    fun init_idArgumentPassed_repositoryThrowsException_emitsStateWithErrorStatus() {
        coEvery { repository.read(any()) } throws Exception()
        val state =
            createViewModel(SavedStateHandle(mapOf(AddEdit.ID_ARG to reminder1.id))).uiState.value

        assertEquals(true, state.isError)
    }

    @Test
    fun onNameChanged_emitsStateWithUpdatedName() {
        val viewModel = createViewModel()
        viewModel.onNameChanged("newName")

        assertEquals("newName", viewModel.uiState.value.name)
    }

    @Test
    fun onDateChanged_emitsStateWithUpdatedDate() {
        val viewModel = createViewModel()
        viewModel.onDateChanged(12345)

        assertEquals(12345, viewModel.uiState.value.expirationDateMillis)
    }

    @Test
    fun onDateChanged_dateMillisIsNull_doesNotEmitNewState() {
        val viewModel = createViewModel()
        val initialDateMillis = viewModel.uiState.value.expirationDateMillis
        viewModel.onDateChanged(null)

        assertEquals(initialDateMillis, viewModel.uiState.value.expirationDateMillis)
    }

    @Test
    fun onTimeChanged_emitsStateWithUpdatedTime() {
        val viewModel = createViewModel()
        viewModel.onTimeChanged(13, 30)

        assertEquals(13, viewModel.uiState.value.expirationHour)
        assertEquals(30, viewModel.uiState.value.expirationMinute)
    }

    @Test
    fun onAdvanceUnitChanged_emitsStateWithUpdatedAdvanceUnit() {
        val viewModel = createViewModel()
        viewModel.onAdvanceUnitChanged(0)

        assertEquals(DateTimeUnit.MINUTE, viewModel.uiState.value.advanceUnit)
    }

    @Test
    fun onAdvanceValueChanged_emitsStateWithUpdatedAdvanceValue() {
        val viewModel = createViewModel()
        viewModel.onAdvanceValueChanged(22)

        assertEquals(22, viewModel.uiState.value.advanceValue)
    }

    @Test
    fun toggleShowingDatePicker_emitsStateWithUpdatedValue() {
        val viewModel = createViewModel()
        viewModel.toggleShowingDatePicker()

        assertEquals(true, viewModel.uiState.value.showDatePicker)
    }

    @Test
    fun toggleShowingTimePicker_emitsStateWithUpdatedValue() {
        val viewModel = createViewModel()
        viewModel.toggleShowingTimePicker()

        assertEquals(true, viewModel.uiState.value.showTimePicker)
    }

    @Test
    fun toggleShowingAdvancePicker_emitsStateWithUpdatedValue() {
        val viewModel = createViewModel()
        viewModel.toggleShowingAdvancePicker()

        assertEquals(true, viewModel.uiState.value.showAdvancePicker)
    }

    @Test
    fun saveReminder_newReminder_callsRepositoryCreateMethod() {
        createViewModel().saveReminder()

        coVerify(exactly = 1) { repository.create(any()) }
    }

    @Test
    fun saveReminder_existingReminder_callsRepositoryUpdateMethod() {
        coEvery { repository.read(any()) } returns reminder1
        createViewModel(SavedStateHandle(mapOf(AddEdit.ID_ARG to reminder1.id))).saveReminder()

        coVerify(exactly = 1) { repository.update(any()) }
    }

    @Test
    fun saveReminder_successfullySaved_emitsStateWithSavedStatus() {
        coEvery { repository.read(any()) } returns reminder2
        coEvery { repository.update(any()) } returns Unit
        val viewModel = createViewModel(SavedStateHandle(mapOf(AddEdit.ID_ARG to reminder2.id)))
        viewModel.saveReminder()
        val state = viewModel.uiState.value

        assertEquals(true, state.isSaved)
    }

    @Test
    fun saveReminder_errorOccurred_emitsStateWithErrorStatus() {
        coEvery { repository.read(any()) } returns reminder3
        coEvery { repository.create(any()) } throws Exception()
        val viewModel = createViewModel(SavedStateHandle(mapOf(AddEdit.ID_ARG to reminder3.id)))
        viewModel.saveReminder()
        val state = viewModel.uiState.value

        assertEquals(true, state.isError)
    }
}