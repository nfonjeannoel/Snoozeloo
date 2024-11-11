package com.camgist.snoozeloo.alarm.domain

data class AlarmItem(
    val id: Long,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean,
    val alarmName: String?
)