package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Assert.assertEquals
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
class AdvancePickerModalTest {

    private val context = RuntimeEnvironment.getApplication()
    private val advanceUnits = listOf("minute", "hour", "day")
    private var isCloseButtonClicked = false
    private var advanceUnit = 0
    private var advanceValue = 0

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            AdvancePickerModal(
                advanceUnits = advanceUnits,
                selectedAdvanceUnit = "minute",
                advanceValues = (1..5).toList(),
                selectedAdvanceValue = 0,
                onAdvanceUnitSelected = { advanceUnit = it },
                onAdvanceValueSelected = { advanceValue = it },
                onDismiss = { isCloseButtonClicked = true },
            )
        }
    }

    @Test
    fun title_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.add_edit_advance_title))
            .assertIsDisplayed()
    }

    @Test
    fun closeButton_clicked_callsOnDismiss() {
        composeTestRule.onNodeWithText(context.getString(R.string.close_button_text))
            .assertIsDisplayed()
            .performClick()

        assertTrue(isCloseButtonClicked)
    }

    @Test
    fun listOfAdvanceUnits_isDisplayedAndItemsAreSelectable() {
        with(composeTestRule) {
            onNodeWithText("minute").assertIsDisplayed().assertIsSelectable()
            onNodeWithText("hour").assertIsDisplayed().assertIsSelectable()
            onNodeWithText("day").assertIsDisplayed().assertIsSelectable()
        }
    }

    @Test
    fun listOfAdvanceValues_isDisplayedAndItemsAreClickable() {
        with(composeTestRule) {
            for (i in 1..5) {
                onNodeWithText("$i").assertIsDisplayed().assertHasClickAction()
            }
        }
    }

    @Test
    fun advanceUnit_clicked_callsOnAdvanceUnitSelected() {
        composeTestRule.onNodeWithText("day").performClick()

        assertEquals(2, advanceUnit)
    }

    @Test
    fun advanceValue_clicked_callsOnAdvanceValueSelected() {
        composeTestRule.onNodeWithText("5").performClick()

        assertEquals(5, advanceValue)
    }
}