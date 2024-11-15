package com.camgist.snoozeloo.alarm.presentation.alarm_trigger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.launch

class ViewModelAlarmTrigger(
    private val navigator: Navigator
): ViewModel() {

    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}