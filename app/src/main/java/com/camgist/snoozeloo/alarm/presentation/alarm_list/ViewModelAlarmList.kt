package com.camgist.snoozeloo.alarm.presentation.alarm_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.launch

class ViewModelAlarmList(
    private val navigator: Navigator
): ViewModel() {

    fun navigateToDetailScreen(id: String) {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.DetailScreen(id)
            )
        }
    }
    fun navigateToTriggerScreen() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.TriggerScreen
            )
        }
    }
}