package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
class InputSelectorTest {

    private val context = RuntimeEnvironment.getApplication()
    private var isClicked = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            InputSelector(
                title = R.string.add_edit_time_title,
                textValue = "10:11",
                icon = ImageVector.vectorResource(R.drawable.schedule),
                iconDescriptionText = R.string.add_edit_time_icon_description,
                onClick = { isClicked = true }
            )
        }
    }

    @Test
    fun title_isDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.add_edit_time_title))
            .assertIsDisplayed()
    }

    @Test
    fun textValue_isDisplayed() {
        composeTestRule.onNodeWithText("10:11").assertIsDisplayed()
    }

    @Test
    fun icon_isDisplayed() {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_edit_time_icon_description))
            .assertIsDisplayed()
    }

    @Test
    fun inputSelector_clicked_callsOnClick() {
        composeTestRule.onNodeWithText("10:11").assertIsDisplayed().performClick()

        assertTrue(isClicked)
    }
}