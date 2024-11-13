package com.camgist.snoozeloo.alarm.presentation.models

import com.camgist.snoozeloo.alarm.domain.AlarmItem

data class AlarmItemUi(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean,
    val alarmName: String?
)

fun AlarmItem.toAlarmItemUi(): AlarmItemUi {
    return AlarmItemUi(
        id = id,
        hour = hour,
        minute = minute,
        isEnabled = isEnabled,
        alarmName = alarmName
    )
}