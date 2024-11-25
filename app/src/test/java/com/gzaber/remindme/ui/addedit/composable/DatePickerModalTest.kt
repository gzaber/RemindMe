package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DatePickerModalTest {

    private val context = RuntimeEnvironment.getApplication()
    private var isConfirmButtonClicked = false
    private var isDismissButtonClicked = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DatePickerModal(
                initialDateMillis = 1000,
                onDateSelected = { _ -> isConfirmButtonClicked = true },
                onDismiss = { isDismissButtonClicked = true }
            )
        }
    }

    @Test
    fun datePicker_isDisplayed() {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.date_picker_content_description))
            .assertIsDisplayed()
    }

    @Test
    fun confirmButton_clicked_callsOnDateSelected() {
        composeTestRule.onNodeWithText(context.getString(R.string.confirm_button_text))
            .assertIsDisplayed().performClick()

        assertTrue(isConfirmButtonClicked)
    }

    @Test
    fun dismissButton_clicked_callsOnDismiss() {
        composeTestRule.onNodeWithText(context.getString(R.string.dismiss_button_text))
            .assertIsDisplayed().performClick()

        assertTrue(isDismissButtonClicked)
    }
}