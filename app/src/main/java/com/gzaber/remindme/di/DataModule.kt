package com.gzaber.remindme.di

import android.app.AlarmManager
import android.app.NotificationManager
import androidx.room.Room
import com.gzaber.remindme.data.repository.DefaultRemindersRepository
import com.gzaber.remindme.data.repository.RemindersRepository
import com.gzaber.remindme.data.service.AlarmReceiver
import com.gzaber.remindme.data.service.AlarmService
import com.gzaber.remindme.data.service.DefaultAlarmService
import com.gzaber.remindme.data.service.DefaultNotificationService
import com.gzaber.remindme.data.service.NotificationService
import com.gzaber.remindme.data.source.RemindersDao
import com.gzaber.remindme.data.source.RemindersDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<AlarmManager> {
        androidContext().getSystemService(AlarmManager::class.java)
    }
    single<NotificationManager> {
        androidContext().getSystemService(NotificationManager::class.java)
    }
    single<AlarmReceiver> {
        AlarmReceiver(get())
    }
    single<AlarmService> {
        DefaultAlarmService(get(), androidContext())
    }
    single<NotificationService> {
        DefaultNotificationService(get(), androidContext())
    }
    single<RemindersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            RemindersDatabase::class.java,
            "reminders_db"
        ).build()
    }
    single<RemindersDao> {
        get<RemindersDatabase>().remindersDao()
    }
    single<RemindersRepository> {
        DefaultRemindersRepository(get(), get())
    }
}