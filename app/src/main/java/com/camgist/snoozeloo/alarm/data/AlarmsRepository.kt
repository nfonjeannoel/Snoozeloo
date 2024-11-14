package com.camgist.snoozeloo.alarm.data

import com.camgist.snoozeloo.alarm.data.database.AlarmsDao
import com.camgist.snoozeloo.alarm.data.database.toAlarmItem
import com.camgist.snoozeloo.alarm.data.database.toAlarmItemEntity
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmsRepository(private val alarmsDao: AlarmsDao) : BaseAlarmsRepository {
    override fun getAllAlarmsStream(): Flow<List<AlarmItem>> {
        return alarmsDao.getAllAlarmItems().map { alarmItems ->
            alarmItems.map { it.toAlarmItem() }
        }
    }

    override suspend fun getAlarmStream(id: Int): Flow<AlarmItem?> {
        return alarmsDao.getAlarmItem(id).map { it.toAlarmItem() }
    }

    override suspend fun insertAlarm(alarmItem: AlarmItem) {
        alarmsDao.insertAlarmItem(alarmItem.toAlarmItemEntity())
    }

    override suspend fun updateAlarm(alarmItem: AlarmItem) {
        alarmsDao.updateAlarmItem(alarmItem.toAlarmItemEntity())
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        alarmsDao.deleteAlarmItem(alarmItem.toAlarmItemEntity())
    }
}