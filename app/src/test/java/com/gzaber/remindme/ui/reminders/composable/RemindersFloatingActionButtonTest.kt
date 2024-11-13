package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
class RemindersFloatingActionButtonTest {

    private val context = RuntimeEnvironment.getApplication()
    private var isFabClicked = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            RemindersFloatingActionButton(
                onClick = { isFabClicked = true }
            )
        }
    }

    @Test
    fun icon_isDisplayed() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.fab_content_description))
            .assertIsDisplayed()
    }

    @Test
    fun fab_clicked_callsOnClick() {
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.fab_content_description))
            .assertIsDisplayed()
            .performClick()
        assertTrue(isFabClicked)
    }
}