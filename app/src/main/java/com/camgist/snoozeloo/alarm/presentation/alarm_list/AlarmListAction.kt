package com.camgist.snoozeloo.alarm.presentation.alarm_list

import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi

sealed interface AlarmListAction {
    data class OnAlarmClicked(val alarmItemUi: AlarmItemUi) : AlarmListAction
    data class OnCheckChanged(val alarmItemUi: AlarmItemUi, val isChecked: Boolean) : AlarmListAction
    data object OnAddAlarmClicked : AlarmListAction
}