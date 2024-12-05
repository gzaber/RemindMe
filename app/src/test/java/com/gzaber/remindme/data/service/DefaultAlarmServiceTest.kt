package com.gzaber.remindme.data.service

import android.app.AlarmManager
import android.app.PendingIntent
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
class DefaultAlarmServiceTest {

    private lateinit var alarmService: AlarmService
    private lateinit var alarmManager: AlarmManager
    private val context = RuntimeEnvironment.getApplication()
    private val title = "title"
    private val content = "content"
    private val requestCode = 7
    private val dateTimeMillis = 999L

    @Before
    fun setUp() {
        alarmManager = mockk<AlarmManager>()
        every { alarmManager.setExactAndAllowWhileIdle(any(), any(), any()) } returns Unit
        every { alarmManager.cancel(any(PendingIntent::class)) } returns Unit
        alarmService = DefaultAlarmService(alarmManager, context)
    }

    @Test
    fun schedule_callsAlarmManagerSetExactAndAllowWhileIdleMethod() {
        alarmService.schedule(requestCode, title, content, dateTimeMillis)

        verify(exactly = 1) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateTimeMillis,
                any(PendingIntent::class)
            )
        }
    }

    @Test
    fun delete_callsAlarmManagerCancelMethod() {
        alarmService.delete(requestCode)

        verify(exactly = 1) {
            alarmManager.cancel(any(PendingIntent::class))
        }
    }
}