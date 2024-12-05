package com.gzaber.remindme.ui.reminders

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.remindme.R
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import com.gzaber.remindme.helper.plus
import com.gzaber.remindme.shared.atPresent
import com.gzaber.remindme.shared.minus
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class RemindersScreenTest {

    private lateinit var repository: RemindersRepository
    private lateinit var viewModel: RemindersViewModel
    private var isOnNavigateToAddEditClicked = false
    private val now = Clock.atPresent()
    private val reminder1 = Reminder(id = 1, "name1", now.minus(DateTimePeriod(days = 1)), now)
    private val reminder2 = Reminder(id = 2, "name2", now.plus(DateTimePeriod(hours = 6)), now)
    private val reminders = listOf(reminder1, reminder2)
    private val context = RuntimeEnvironment.getApplication()

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun appBar_displaysTitle() {
        setUpScreen()

        composeTestRule.onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
    }

    @Test
    fun content_loadingData_displaysLoadingIndicator() {
        setUpScreen(loadingDelayMillis = 300)

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.loading_indicator_content_description)
        ).assertIsDisplayed()
    }

    @Test
    fun content_dataSuccessfullyLoaded_displayReminders() {
        setUpScreen()

        with(composeTestRule) {
            onNodeWithText("name1").assertIsDisplayed()
            onNodeWithText("name2").assertIsDisplayed()
        }
    }

    @Test
    fun content_repositoryErrorOccurred_displaysSnackBarWithMessage() {
        setUpScreen(throwException = true)

        composeTestRule.onNodeWithText(context.getString(R.string.error_message))
            .assertIsDisplayed()
    }

    @Test
    fun reminderItem_deleteMenuOptionClicked_displaysDeleteDialog() {
        setUpScreen()

        with(composeTestRule) {
            onAllNodesWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .onFirst()
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_delete_option))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.delete_reminder_dialog_title)).assertIsDisplayed()
        }
    }

    @Test
    fun reminderItem_deleteReminderConfirmed_closesDeleteDialog() {
        setUpScreen()

        with(composeTestRule) {
            onAllNodesWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .onFirst()
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_delete_option))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.confirm_button_text))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.delete_reminder_dialog_title)).assertIsNotDisplayed()
        }
    }

    @Test
    fun reminderItem_updateMenuOptionClicked_callsOnNavigateToAddEdit() {
        setUpScreen()

        assertFalse(isOnNavigateToAddEditClicked)
        with(composeTestRule) {
            onAllNodesWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .onFirst()
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_update_option))
                .assertIsDisplayed()
                .performClick()
        }
        assertTrue(isOnNavigateToAddEditClicked)
    }

    @Test
    fun fab_clicked_callsOnNavigateToAddEdit() {
        setUpScreen()

        assertFalse(isOnNavigateToAddEditClicked)
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.fab_content_description))
            .assertIsDisplayed()
            .performClick()
        assertTrue(isOnNavigateToAddEditClicked)
    }

    private fun setUpScreen(loadingDelayMillis: Long = 0, throwException: Boolean = false) {
        repository = mockk<RemindersRepository>()
        every { repository.observeAll() } returns
                flow {
                    delay(loadingDelayMillis)
                    emit(reminders)
                }.onStart { if (throwException) throw Exception() }
        viewModel = RemindersViewModel(remindersRepository = repository)

        composeTestRule.setContent {
            RemindersScreen(
                onNavigateToAddEdit = { isOnNavigateToAddEditClicked = true },
                viewModel = viewModel
            )
        }
    }
}