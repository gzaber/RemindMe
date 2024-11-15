package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestActivity
import com.gzaber.remindme.ui.reminders.model.ExpirationStatus
import com.gzaber.remindme.ui.reminders.model.UiReminder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class RemindersContentTest {

    private val context = RuntimeEnvironment.getApplication()
    private val uiReminders = listOf(
        UiReminder(
            id = 1,
            name = "reminder1",
            formattedExpiration = "2024",
            expirationStatus = ExpirationStatus.EXPIRED
        ),
        UiReminder(
            id = 2,
            name = "reminder2",
            formattedExpiration = "2025",
            expirationStatus = ExpirationStatus.MORE
        )
    )

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun emptyListOfReminders_displaysEmptyListInfo() {
        setUpRemindersContent(reminders = listOf())

        composeTestRule.onNodeWithText(context.getString(R.string.empty_reminders_list_info))
            .assertIsDisplayed()
    }

    @Test
    fun names_areDisplayed() {
        setUpRemindersContent()

        with(composeTestRule) {
            onNodeWithText("reminder1").assertIsDisplayed()
            onNodeWithText("reminder2").assertIsDisplayed()
        }
    }

    @Test
    fun expirationDates_areDisplayed() {
        setUpRemindersContent()

        with(composeTestRule) {
            onNodeWithText("2024").assertIsDisplayed()
            onNodeWithText("2025").assertIsDisplayed()
        }
    }

    @Test
    fun expirationDateIcons_areDisplayed() {
        setUpRemindersContent()

        with(composeTestRule) {
            onAllNodesWithContentDescription(context.getString(R.string.reminder_list_item_icon_description))
                .assertCountEquals(2).onFirst().assertIsDisplayed()
            onAllNodesWithContentDescription(context.getString(R.string.reminder_list_item_icon_description))
                .assertCountEquals(2).onLast().assertIsDisplayed()
        }
    }

    @Test
    fun menuButtons_canBeClicked() {
        setUpRemindersContent()

        with(composeTestRule) {
            onAllNodesWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .assertCountEquals(2).onFirst().assertHasClickAction()
            onAllNodesWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .assertCountEquals(2).onLast().assertHasClickAction()
        }
    }

    private fun setUpRemindersContent(reminders: List<UiReminder> = uiReminders) {
        composeTestRule.setContent {
            RemindersContent(
                reminders = reminders,
                onUpdateReminder = {},
                onDeleteReminder = {},
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}