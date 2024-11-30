package com.gzaber.remindme.ui.addedit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.SavedStateHandle
import com.gzaber.remindme.AddEdit
import com.gzaber.remindme.R
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.helper.MainDispatcherRule
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import com.gzaber.remindme.shared.minus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AddEditScreenTest {

    private lateinit var repository: RemindersRepository
    private lateinit var viewModel: AddEditViewModel
    private var isOnNavigateBackClicked = false
    private val date = LocalDate(year = 2024, month = Month.DECEMBER, dayOfMonth = 6)
    private val time = LocalTime(hour = 13, minute = 45)
    private val dateTime = LocalDateTime(date, time)
    private val reminder =
        Reminder(
            id = 1,
            name = "name1",
            advance = dateTime.minus(DateTimePeriod(days = 1)),
            expiration = dateTime
        )
    private val context = RuntimeEnvironment.getApplication()

    @get:Rule(order = 0)
    val rule = MainDispatcherRule()

    @get:Rule(order = 1)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @Test
    fun appBarTitle_isDisplayed() {
        setUpScreen()

        composeTestRule.onNodeWithText(context.getString(R.string.add_edit_app_bar_update_title))
            .assertIsDisplayed()
    }

    @Test
    fun content_reminderSuccessfullyLoaded_displaysReminderComponents() {
        setUpScreen()

        with(composeTestRule) {
            onNodeWithText("name1").assertIsDisplayed()
            onNodeWithText("6 December 2024").assertIsDisplayed()
            onNodeWithText("13:45").assertIsDisplayed()
            onNodeWithContentDescription(context.getString(R.string.add_edit_advance_icon_description))
            onNodeWithText("1 day before").performScrollTo().assertIsDisplayed()
        }
    }

    @Test
    fun content_repositoryErrorOccurred_displaysSnackBarWithMessage() {
        setUpScreen(throwException = true)

        composeTestRule.onNodeWithText(context.getString(R.string.error_message))
            .assertIsDisplayed()
    }

    @Test
    fun backButton_clicked_callsOnNavigateBack() {
        setUpScreen()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_edit_close_icon_description))
            .assertIsDisplayed()
            .performClick()

        assertTrue(isOnNavigateBackClicked)
    }

    @Test
    fun saveButton_clicked_callsOnNavigateBack() = runTest {
        setUpScreen()

        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.add_edit_save_icon_description))
                .assertIsDisplayed()
                .performClick()
            awaitIdle()
        }

        assertTrue(isOnNavigateBackClicked)
    }

    @Test
    fun dateInput_clicked_displaysDatePicker() {
        setUpScreen()

        with(composeTestRule) {
            onNodeWithText("6 December 2024").assertIsDisplayed()
                .performClick()
            onNodeWithContentDescription(context.getString(R.string.date_picker_modal_content_description))
                .assertIsDisplayed()
        }
    }

    @Test
    fun timeInput_clicked_displaysTimePicker() {
        setUpScreen()

        with(composeTestRule) {
            onNodeWithText("13:45").assertIsDisplayed().performClick()
            onNodeWithContentDescription(context.getString(R.string.time_picker_modal_content_description))
                .assertIsDisplayed()
        }
    }

    @Test
    fun advanceInput_clicked_displaysAdvancePicker() {
        setUpScreen()

        with(composeTestRule) {
            onNodeWithText("1 day before").performScrollTo().assertIsDisplayed().performClick()
            onNodeWithContentDescription(context.getString(R.string.advance_picker_modal_content_description))
                .assertIsDisplayed()
        }
    }

    private fun setUpScreen(throwException: Boolean = false) {
        repository = mockk<RemindersRepository>()
        when {
            throwException -> coEvery { repository.read(any()) } throws Exception()
            else -> coEvery { repository.read(any()) } returns reminder
        }
        coEvery { repository.update(any()) } returns Unit

        viewModel =
            AddEditViewModel(
                remindersRepository = repository,
                savedStateHandle = SavedStateHandle(
                    mapOf(
                        AddEdit.ID_ARG to reminder.id
                    )
                )
            )

        composeTestRule.setContent {
            AddEditScreen(
                title = R.string.add_edit_app_bar_update_title,
                onNavigateBack = { isOnNavigateBackClicked = true },
                viewModel = viewModel
            )
        }
    }
}