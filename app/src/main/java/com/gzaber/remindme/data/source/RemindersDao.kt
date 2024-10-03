package com.gzaber.remindme.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(reminder: ReminderEntity): Long

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun read(id: Int): ReminderEntity

    @Query("SELECT * FROM reminders")
    fun observeAll(): Flow<List<ReminderEntity>>
}