package com.camgist.snoozeloo.alarm.domain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class AlarmItem(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean,
    val alarmName: String?
) {
    val time: LocalDateTime
        get() = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(hour, minute))
}
