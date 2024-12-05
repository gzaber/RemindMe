package com.gzaber.remindme.data.service

import android.app.Notification
import android.app.NotificationManager
import com.gzaber.remindme.helper.TestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class DefaultNotificationServiceTest {

    private lateinit var notificationService: DefaultNotificationService
    private lateinit var notificationManager: NotificationManager
    private val context = RuntimeEnvironment.getApplication()
    private val notificationId = 7
    private val title = "title"
    private val content = "content"

    @Before
    fun setUp() {
        notificationManager = mockk<NotificationManager>()
        every { notificationManager.createNotificationChannel(any()) } returns Unit
        every { notificationManager.notify(any(), any()) } returns Unit
        notificationService = DefaultNotificationService(notificationManager, context)
    }

    @Test
    fun send_callsNotificationManagerNotifyMethod() {
        notificationService.send(notificationId, title, content)

        verify(exactly = 1) {
            notificationManager.notify(notificationId, any(Notification::class))
        }
    }
}