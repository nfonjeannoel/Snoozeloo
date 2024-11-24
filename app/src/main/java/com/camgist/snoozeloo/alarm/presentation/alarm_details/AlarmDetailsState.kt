package com.camgist.snoozeloo.alarm.presentation.alarm_details

import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import java.time.LocalTime

data class AlarmDetailsState(
    val alarmItemUi: AlarmItemUi? = null,
    val hours: String = "",
    val minutes: String = "",
    val alarmName: String? = "",
    val isTimeValid: Boolean = false,
    val errorMessage: String? = null,
    val showNameDialog: Boolean = false,
    val tempAlarmName: String? = "",
    val nextAlarmText: String = "",
)


fun AlarmDetailsState.toLocalTime(): LocalTime {
    val hours = hours.toIntOrNull() ?: 0
    val minutes = minutes.toIntOrNull() ?: 0
    return LocalTime.of(hours, minutes)
}