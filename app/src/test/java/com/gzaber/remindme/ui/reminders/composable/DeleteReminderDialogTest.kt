package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Assert.assertFalse
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
class DeleteReminderDialogTest {

    private val context = RuntimeEnvironment.getApplication()
    private var isConfirmButtonClicked = false
    private var isDismissButtonClicked = false

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DeleteReminderDialog(
                onDismissRequest = { isDismissButtonClicked = true },
                onConfirmation = { isConfirmButtonClicked = true }
            )
        }
    }

    @Test
    fun title_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.delete_reminder_dialog_title))
            .assertIsDisplayed()
    }

    @Test
    fun contentText_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.delete_reminder_dialog_text))
            .assertIsDisplayed()
    }

    @Test
    fun buttons_areDisplayed() {
        with(composeTestRule) {
            onNodeWithText(context.getString(R.string.confirm_button_text))
                .assertIsDisplayed().assertHasClickAction()
            onNodeWithText(context.getString(R.string.dismiss_button_text))
                .assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun confirmButton_clicked_callsOnConfirmation() {
        assertFalse(isConfirmButtonClicked)
        composeTestRule.onNodeWithText(context.getString(R.string.confirm_button_text))
            .assertIsDisplayed()
            .performClick()
        assertTrue(isConfirmButtonClicked)
    }

    @Test
    fun dismissButton_clicked_callsOnDismissRequest() {
        assertFalse(isDismissButtonClicked)
        composeTestRule.onNodeWithText(context.getString(R.string.dismiss_button_text))
            .assertIsDisplayed()
            .performClick()
        assertTrue(isDismissButtonClicked)
    }
}