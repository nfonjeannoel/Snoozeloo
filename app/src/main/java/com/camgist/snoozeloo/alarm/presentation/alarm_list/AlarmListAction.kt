package com.camgist.snoozeloo.alarm.presentation.alarm_list

import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi

sealed interface AlarmListAction {
    data class OnAlarmClicked(val alarmItemUi: AlarmItemUi) : AlarmListAction
    data object OnAddAlarmClicked : AlarmListAction
}