package com.gzaber.remindme.ui.reminders.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class EmptyListInfoTest {

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun infoText_isDisplayed() {
        val context = RuntimeEnvironment.getApplication()
        with(composeTestRule) {
            setContent {
                EmptyListInfo(contentPadding = PaddingValues(0.dp))
            }
            onNodeWithText(context.getString(R.string.empty_reminders_list_info))
                .assertIsDisplayed()
        }
    }
}