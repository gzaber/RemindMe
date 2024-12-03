package com.gzaber.remindme.di

import android.app.AlarmManager
import android.app.NotificationManager
import androidx.lifecycle.SavedStateHandle
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.service.AlarmService
import com.gzaber.remindme.data.service.NotificationService
import com.gzaber.remindme.data.source.RemindersDao
import com.gzaber.remindme.data.source.RemindersDatabase
import com.gzaber.remindme.helper.KoinTestRule
import com.gzaber.remindme.helper.RobolectricTestRule
import com.gzaber.remindme.helper.TestApplication
import com.gzaber.remindme.ui.addedit.AddEditViewModel
import com.gzaber.remindme.ui.reminders.RemindersViewModel
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.verify.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(KoinExperimentalAPI::class)
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AppModulesTest : KoinTest {

    @get:Rule(order = 0)
    val robolectricTestRule = RobolectricTestRule()

    @get:Rule(order = 1)
    val koinTestRule = KoinTestRule(
        modules = listOf(
            dataModule,
            viewModelModule,
            module { single { SavedStateHandle() } })
    )

    @Test
    fun checkKoinModules() {
        val appModules = module {
            includes(
                dataModule,
                viewModelModule
            )
        }

        appModules.verify(extraTypes = listOf(SavedStateHandle::class))
    }

    @Test
    fun dataModule_AlarmManagerRequested_providesInstance() {
        val alarmManager = get<AlarmManager>()

        assertNotNull(alarmManager)
    }

    @Test
    fun dataModule_NotificationManagerRequested_providesInstance() {
        val notificationManager = get<NotificationManager>()

        assertNotNull(notificationManager)
    }

    @Test
    fun dataModule_AlarmServiceRequested_providesInstance() {
        val alarmService = get<AlarmService>()

        assertNotNull(alarmService)
    }

    @Test
    fun dataModule_NotificationServiceRequested_providesInstance() {
        val notificationService = get<NotificationService>()

        assertNotNull(notificationService)
    }

    @Test
    fun dataModule_RemindersDatabaseRequested_providesInstance() {
        val database = get<RemindersDatabase>()

        assertNotNull(database)
    }

    @Test
    fun dataModule_RemindersDaoRequested_providesInstance() {
        val dao = get<RemindersDao>()

        assertNotNull(dao)
    }

    @Test
    fun dataModule_RemindersRepositoryRequested_providesInstance() {
        val repository = get<RemindersRepository>()

        assertNotNull(repository)
    }

    @Test
    fun viewModelModule_RemindersViewModelRequested_providesInstance() {
        val viewModel = get<RemindersViewModel>()

        assertNotNull(viewModel)
    }

    @Test
    fun viewModelModule_AddEditViewModelRequested_providesInstance() {
        val viewModel = get<AddEditViewModel>()

        assertNotNull(viewModel)
    }
}