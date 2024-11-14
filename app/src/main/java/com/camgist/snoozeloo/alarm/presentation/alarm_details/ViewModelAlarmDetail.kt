package com.camgist.snoozeloo.alarm.presentation.alarm_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.launch

class ViewModelAlarmDetail(
    private val navigator: Navigator
): ViewModel() {

    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}