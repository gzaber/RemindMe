package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class ReminderMenuButtonTest {

    private val context = RuntimeEnvironment.getApplication()
    private var isUpdateOptionClicked = false
    private var isDeleteOptionClicked = false

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ReminderMenuButton(
                onUpdateClick = { isUpdateOptionClicked = true },
                onDeleteClick = { isDeleteOptionClicked = true }
            )
        }
    }

    @Test
    fun icon_isDisplayed() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
            .assertIsDisplayed()
    }

    @Test
    fun button_canBeClicked() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun button_clicked_menuOptionsAreDisplayed() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_update_option)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.reminder_menu_button_delete_option)).assertIsDisplayed()
        }
    }

    @Test
    fun updateOption_clicked_callsOnUpdateClick() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_update_option))
                .assertIsDisplayed()
                .performClick()
        }
        assertTrue(isUpdateOptionClicked)
    }

    @Test
    fun deleteOption_clicked_callsOnDeleteClick() {
        with(composeTestRule) {
            onNodeWithContentDescription(context.getString(R.string.reminder_menu_button_icon_description))
                .assertIsDisplayed()
                .performClick()
            onNodeWithText(context.getString(R.string.reminder_menu_button_delete_option))
                .assertIsDisplayed()
                .performClick()
        }
        assertTrue(isDeleteOptionClicked)
    }
}