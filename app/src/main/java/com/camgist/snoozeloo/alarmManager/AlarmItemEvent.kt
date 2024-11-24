package com.camgist.snoozeloo.alarmManager

import java.time.LocalDateTime

data class AlarmItemEvent(
    val time: LocalDateTime,
    val alarmTitle: String
)