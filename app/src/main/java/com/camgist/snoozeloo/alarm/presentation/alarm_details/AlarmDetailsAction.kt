package com.camgist.snoozeloo.alarm.presentation.alarm_details

import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi

sealed interface AlarmDetailsAction {
    data class OnHoursChanged(val hours: String) : AlarmDetailsAction
    data class OnMinutesChanged(val minutes: String) : AlarmDetailsAction
    data object OnSaveClicked : AlarmDetailsAction
    data object OnCloseClicked : AlarmDetailsAction
    data object OnAlarmNameClicked : AlarmDetailsAction
    data class OnAlarmNameChanged(val alarmName: String) : AlarmDetailsAction
    data object OnSaveAlarmNameClicked : AlarmDetailsAction
    data object OnDismissNameDialog : AlarmDetailsAction
    data class OnDeleteClicked(val alarmItemUi: AlarmItemUi) : AlarmDetailsAction
}