package com.camgist.snoozeloo.alarm.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Update
    suspend fun updateAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Delete
    suspend fun deleteAlarmItem(alarmItemEntity: AlarmItemEntity)

    @Query("SELECT * FROM alarm_items WHERE id = :id")
    fun getAlarmItem(id: Int): Flow<AlarmItemEntity>

    @Query("SELECT * FROM alarm_items WHERE id = :id")
    fun getAlarmById(id: Int): AlarmItemEntity

    @Query("SELECT * FROM alarm_items")
    fun getAllAlarmItems(): Flow<List<AlarmItemEntity>>
}