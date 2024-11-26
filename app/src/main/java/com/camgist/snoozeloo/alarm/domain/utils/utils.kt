package com.camgist.snoozeloo.alarm.domain.utils

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

fun calculateNextAlarmText(alarmTime: LocalTime): String {
    val now = LocalDateTime.now()
    var nextAlarm = now.with(alarmTime)

    // If the alarm time has already passed today, schedule for tomorrow
    if (now.toLocalTime().isAfter(alarmTime)) {
        nextAlarm = nextAlarm.plusDays(1)
    }

    val days = ChronoUnit.DAYS.between(now, nextAlarm)
    val hours = ChronoUnit.HOURS.between(now, nextAlarm) % 24
    val minutes = ChronoUnit.MINUTES.between(now, nextAlarm) % 60

    return "Alarm in " + buildString {
        if (days > 0) append("$days d ")
        if (hours > 0) append("$hours h ")
        if (minutes > 0) append("$minutes min")
        if (days == 0L && hours == 0L && minutes == 0L) append("less than a minute")
    }.trim()
}

fun calculateNextAlarmText(hour: Int, minutes: Int): String {
    val alarmTime = LocalTime.of(hour, minutes)
    return calculateNextAlarmText(alarmTime)
}