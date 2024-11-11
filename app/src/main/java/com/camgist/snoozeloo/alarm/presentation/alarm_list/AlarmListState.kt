package com.camgist.snoozeloo.alarm.presentation.alarm_list

import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi

data class AlarmListState(
    val alarmUiItems: List<AlarmItemUi> = emptyList(),
    val selectedAlarmUiItem: AlarmItemUi? = null
) {
}