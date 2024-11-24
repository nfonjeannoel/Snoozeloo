package com.camgist.snoozeloo.alarmManager

import com.camgist.snoozeloo.alarm.domain.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItemEvent)
    fun cancel(item: AlarmItemEvent)
}