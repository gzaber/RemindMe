package com.gzaber.remindme.data.service

import android.content.Intent
import com.gzaber.remindme.helper.KoinTestRule
import com.gzaber.remindme.helper.TestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AlarmReceiverTest {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var notificationService: NotificationService
    private val context = RuntimeEnvironment.getApplication()
    private val title = "title"
    private val content = "content"
    private val requestCode = 7
    private val intent = Intent(context, AlarmReceiver::class.java)
        .putExtra(AlarmService.REQUEST_CODE_KEY, requestCode)
        .putExtra(AlarmService.TITLE_KEY, title)
        .putExtra(AlarmService.CONTENT_KEY, content)

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            module { single { notificationService } }
        )
    )

    @Before
    fun setUp() {
        notificationService = mockk<NotificationService>()
        every { notificationService.send(any(), any(), any()) } returns Unit
        alarmReceiver = AlarmReceiver()
    }

    @Test
    fun onReceive_intentExtrasAreNotNull_callsNotificationServiceSendMethodWithProperValues() {
        alarmReceiver.onReceive(context, intent)

        verify(exactly = 1) { notificationService.send(requestCode, title, content) }
    }

    @Test
    fun onReceive_intentExtrasAreNull_callsNotificationServiceSendMethodWithDefaultValues() {
        alarmReceiver.onReceive(context, Intent(context, AlarmReceiver::class.java))

        verify(exactly = 1) { notificationService.send(0, "", "") }
    }

    @Test
    fun onReceive_parametersAreNull_callsNotificationServiceSendMethodWithDefaultValues() {
        alarmReceiver.onReceive(null, null)

        verify(exactly = 1) { notificationService.send(0, "", "") }
    }
}