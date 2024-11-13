package com.camgist.snoozeloo.alarm.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.camgist.snoozeloo.alarm.domain.AlarmItem

@Entity(tableName = "alarm_items")
data class AlarmItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean,
    val alarmName: String?
)


fun AlarmItemEntity.toAlarmItem(): AlarmItem {
    return AlarmItem(
        id = id,
        hour = hour,
        minute = minute,
        isEnabled = isEnabled,
        alarmName = alarmName
    )
}


fun AlarmItem.toAlarmItemEntity(): AlarmItemEntity {
    return AlarmItemEntity(
        id = id,
        hour = hour,
        minute = minute,
        isEnabled = isEnabled,
        alarmName = alarmName
    )
}