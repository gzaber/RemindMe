package com.gzaber.remindme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.gzaber.remindme.di.dataModule
import com.gzaber.remindme.di.viewModelModule
import com.gzaber.remindme.helper.KoinTestRule
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class RemindMeActivityTest : KoinTest {

    private val context = RuntimeEnvironment.getApplication()

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val koinTestRule = KoinTestRule(
        modules = listOf(dataModule, viewModelModule)
    )

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<RemindMeActivity>()

    @Test
    fun activity_displaysAppScreen() {
        composeTestRule.onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
    }
}