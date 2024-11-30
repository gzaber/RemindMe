package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AddEditContentTest {

    private val context = RuntimeEnvironment.getApplication()
    private var name = ""
    private var isDateInputClicked = false
    private var isTimeInputClicked = false
    private var isAdvanceInputClicked = false

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private fun setUpAddEditContent(nameValue: String = "Remind something") {
        composeTestRule.setContent {
            AddEditContent(
                contentPadding = PaddingValues(0.dp),
                nameValue = nameValue,
                dateValue = "2024",
                timeValue = "12:00",
                advanceValue = "1 day",
                onNameChanged = { name = it },
                onDateButtonClick = { isDateInputClicked = true },
                onTimeButtonClick = { isTimeInputClicked = true },
                onAdvanceButtonClick = { isAdvanceInputClicked = true }
            )
        }
    }

    @Test
    fun titles_areDisplayed() {
        setUpAddEditContent()

        with(composeTestRule) {
            onNodeWithText(context.getString(R.string.add_edit_name_title)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.add_edit_date_title)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.add_edit_time_title)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.add_edit_advance_title)).assertIsDisplayed()
        }
    }

    @Test
    fun inputValues_areDisplayed() {
        setUpAddEditContent()

        with(composeTestRule) {
            onNodeWithText("Remind something").assertIsDisplayed()
            onNodeWithText("2024").assertIsDisplayed()
            onNodeWithText("12:00").assertIsDisplayed()
            onNodeWithText("1 day before").assertIsDisplayed()
        }
    }

    @Test
    fun nameInput_isEmpty_placeholderIsDisplayed() {
        setUpAddEditContent(nameValue = "")

        composeTestRule.onNodeWithText(context.getString(R.string.add_edit_name_placeholder))
            .assertIsDisplayed()
    }

    @Test
    fun nameInput_textEntered_callsOnNameChanged() {
        setUpAddEditContent(nameValue = "")

        composeTestRule.onNodeWithText(context.getString(R.string.add_edit_name_placeholder))
            .assertIsDisplayed()
            .performTextInput("reminder name")

        assertEquals("reminder name", name)
    }

    @Test
    fun dateInput_clicked_callsOnDateButtonClick() {
        setUpAddEditContent()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_edit_date_icon_description))
            .assertIsDisplayed().performClick()

        assertTrue(isDateInputClicked)
    }

    @Test
    fun timeInput_clicked_callsOnTimeButtonClick() {
        setUpAddEditContent()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_edit_time_icon_description))
            .assertIsDisplayed().performClick()

        assertTrue(isTimeInputClicked)
    }

    @Test
    fun advanceInput_clicked_callsOnAdvanceButtonClick() {
        setUpAddEditContent()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_edit_advance_icon_description))
            .assertIsDisplayed().performClick()

        assertTrue(isAdvanceInputClicked)
    }
}