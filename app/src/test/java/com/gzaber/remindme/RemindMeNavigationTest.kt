package com.gzaber.remindme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.repository.model.Reminder
import com.gzaber.remindme.helper.KoinTestRule
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import com.gzaber.remindme.shared.minus
import com.gzaber.remindme.ui.addedit.AddEditViewModel
import com.gzaber.remindme.ui.reminders.RemindersViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class RemindMeNavigationTest : KoinTest {

    private lateinit var remindersRepository: RemindersRepository
    private val context = RuntimeEnvironment.getApplication()
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

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @get:Rule(order = 2)
    val koinTestRule = KoinTestRule(
        modules = listOf(
            module {
                single { remindersRepository }
                single { RemindersViewModel(get()) }
                single { AddEditViewModel(get(), SavedStateHandle()) }
            }
        )
    )

    @Before
    fun setUp() {
        remindersRepository = mockk<RemindersRepository>()
        every { remindersRepository.observeAll() } returns
                flow { emit(listOf(reminder)) }
        coEvery { remindersRepository.create(any()) } returns Unit

        composeTestRule.setContent {
            RemindMeNavigation()
        }
    }

    @Test
    fun remindersScreen_fabClicked_navigatesToAddEditScreen() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.fab_content_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.add_edit_app_bar_create_title))
                .assertIsDisplayed()
        }
    }

    @Test
    fun remindersScreen_updateMenuOptionClicked_navigatesToAddEditScreen() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_update_option))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.add_edit_app_bar_update_title))
                .assertIsDisplayed()
        }
    }

    @Test
    fun addEditScreen_backButtonClicked_navigatesBackToRemindersScreen() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.fab_content_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithContentDescription(context.getString(R.string.add_edit_close_icon_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
        }
    }

    @Test
    fun addEditScreen_saveButtonClicked_navigatesBackToRemindersScreen() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.fab_content_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithContentDescription(context.getString(R.string.add_edit_save_icon_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
        }
    }
}