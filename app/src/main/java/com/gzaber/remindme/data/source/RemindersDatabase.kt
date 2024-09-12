package com.gzaber.remindme.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RemindersDatabase : RoomDatabase() {
    abstract fun remindersDao(): RemindersDao
}