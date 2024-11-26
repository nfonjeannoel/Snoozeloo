package com.camgist.snoozeloo.alarm.data

import com.camgist.snoozeloo.alarm.data.database.AlarmItemEntity
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import kotlinx.coroutines.flow.Flow

interface BaseAlarmsRepository {
    fun getAllAlarmsStream(): Flow<List<AlarmItem>>
    suspend fun getAlarmStream(id: Int): Flow<AlarmItem?>
    suspend fun insertAlarm(alarmItem: AlarmItem): Long
    suspend fun updateAlarm(alarmItem: AlarmItem)
    suspend fun deleteAlarm(alarmItem: AlarmItem)
}