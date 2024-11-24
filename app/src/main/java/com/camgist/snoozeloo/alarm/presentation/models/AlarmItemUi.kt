package com.camgist.snoozeloo.alarm.presentation.models

import com.camgist.snoozeloo.alarm.domain.AlarmItem
import java.text.DecimalFormat

data class AlarmItemUi(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean,
    val alarmName: String?
) {


    // Get AM/PM
    fun getAmPm(): String {
        return if (hour < 12) "AM" else "PM"
    }

    // Format minute to always show two digits
    fun getFormattedMinute(): String {
        return DecimalFormat("00").format(minute)
    }

    // Get full formatted time (e.g., "10:00")
    fun getFormattedTime(): String {
        return "${hour}:${getFormattedMinute()}"
    }
}

fun AlarmItem.toAlarmItemUi(): AlarmItemUi {
    return AlarmItemUi(
        id = id,
        hour = hour,
        minute = minute,
        isEnabled = isEnabled,
        alarmName = alarmName
    )
}

fun AlarmItemUi.toAlarmItem(): AlarmItem {
    return AlarmItem(
        id = id,
        hour = hour,
        minute = minute,
        isEnabled = isEnabled,
        alarmName = alarmName
    )
}

