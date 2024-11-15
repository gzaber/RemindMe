package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ReminderListItemTest {

    private val context = RuntimeEnvironment.getApplication()

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ReminderListItem(
                name = "reminder",
                expiration = "2024",
                color = Color.Green,
                isExpired = false,
                onUpdateClick = { },
                onDeleteClick = { }
            )
        }
    }

    @Test
    fun name_isDisplayed() {
        composeTestRule.onNodeWithText("reminder").assertIsDisplayed()
    }

    @Test
    fun expirationDate_isDisplayed() {
        composeTestRule.onNodeWithText("2024").assertIsDisplayed()
    }

    @Test
    fun expirationDateIcon_isDisplayed() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.reminder_list_item_icon_description))
            .assertIsDisplayed()
    }

    @Test
    fun menuButton_canBeClicked() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}