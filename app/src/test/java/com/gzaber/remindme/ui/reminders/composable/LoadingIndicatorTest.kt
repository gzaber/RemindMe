package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class LoadingIndicatorTest {

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun indicator_isDisplayed() {
        val context = RuntimeEnvironment.getApplication()
        with(composeTestRule) {
            setContent {
                LoadingIndicator(contentPadding = PaddingValues(0.dp))
            }
            onNodeWithContentDescription(
                context.getString(R.string.loading_indicator_content_description)
            ).assertIsDisplayed()
        }
    }
}