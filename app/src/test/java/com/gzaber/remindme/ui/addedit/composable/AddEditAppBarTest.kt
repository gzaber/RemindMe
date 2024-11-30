package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.remindme.R
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AddEditAppBarTest {

    private val context = RuntimeEnvironment.getApplication()
    private val titleRes = R.string.add_edit_app_bar_create_title
    private var isNavigateBackButtonClicked = false
    private var isSaveButtonClicked = false

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private fun setUpAppBar(isLoading: Boolean = false) {
        composeTestRule.setContent {
            AddEditAppBar(
                title = titleRes,
                isLoading = isLoading,
                onNavigateBack = { isNavigateBackButtonClicked = true },
                onSave = { isSaveButtonClicked = true }
            )
        }
    }

    @Test
    fun title_isDisplayed() {
        setUpAppBar()

        composeTestRule.onNodeWithText(context.getString(titleRes)).assertIsDisplayed()
    }

    @Test
    fun isLoading_circularProgressIndicatorIsDisplayed() {
        setUpAppBar(isLoading = true)

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.loading_indicator_content_description)
        ).assertIsDisplayed()
    }

    @Test
    fun backButton_clicked_callsOnNavigateBack() {
        setUpAppBar()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.add_edit_close_icon_description))
            .assertIsDisplayed()
            .performClick()

        assertTrue(isNavigateBackButtonClicked)
    }

    @Test
    fun saveButton_clicked_callsOnSave() {
        setUpAppBar()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.add_edit_save_icon_description))
            .assertIsDisplayed()
            .performClick()

        assertTrue(isSaveButtonClicked)
    }
}