package com.camgist.snoozeloo.alarm.presentation.alarm_trigger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.launch

class ViewModelAlarmTrigger(
    private val navigator: Navigator
): ViewModel() {

    private fun navigateBack() {
        Log.d("navigateBack", "navigate back inside viewModel")
        viewModelScope.launch {
            Log.d("navigateBack", "navigate back inside viewModel")
            navigator.navigateUp()
        }
    }

    fun onAction(action: AlarmTriggerAction) {
        when (action) {
            is AlarmTriggerAction.OnTurnOffClicked -> {
                Log.d("navigateBack", "Action = OnTurnOffClicked rechaed inside VM")
                navigateBack()
            }
        }
    }
}