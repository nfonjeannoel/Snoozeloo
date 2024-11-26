package com.camgist.snoozeloo.alarmManager

import com.camgist.snoozeloo.alarm.domain.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}