package com.camgist.snoozeloo.alarm.presentation.alarm_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camgist.snoozeloo.alarmManager.AlarmItemEvent
import com.camgist.snoozeloo.alarmManager.AlarmScheduler
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.launch

class ViewModelAlarmDetail(
    private val navigator: Navigator,
    private val alarmScheduler: AlarmScheduler
): ViewModel() {

    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }

    fun scheduleAlarm(alarmItem: AlarmItemEvent) {
        Log.d("AlarmSchedule", "DetailViewModel: Alarm scheduled")
        alarmScheduler.schedule(alarmItem)
    }
    fun cancelAlarm(alarmItem: AlarmItemEvent) {
        alarmScheduler.cancel(alarmItem)
    }

}