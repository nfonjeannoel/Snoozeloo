package com.camgist.snoozeloo.alarm.presentation.alarm_trigger

sealed interface AlarmTriggerAction {
    data object OnTurnOffClicked: AlarmTriggerAction
}