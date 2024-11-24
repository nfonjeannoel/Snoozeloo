package com.camgist.snoozeloo.alarm.presentation.alarm_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camgist.snoozeloo.alarm.data.BaseAlarmsRepository
import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import com.camgist.snoozeloo.alarm.presentation.models.toAlarmItem
import com.camgist.snoozeloo.alarm.presentation.models.toAlarmItemUi
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModelAlarmList(
    private val navigator: Navigator,
    private val alarmsRepository: BaseAlarmsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AlarmListState())
    val state = _state.onStart {
        // Load alarms
        loadAlarmsFromDb()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), AlarmListState()
    )

    private fun loadAlarmsFromDb() {
        // Load alarms from database
        viewModelScope.launch {
            alarmsRepository.getAllAlarmsStream().map { alarmItems ->
                alarmItems.map { it.toAlarmItemUi() }
            }.collect { alarmUiItems ->
                // Update the state with the collected list of AlarmItemUi
                _state.value = _state.value.copy(alarmUiItems = alarmUiItems)
            }
        }
    }

    fun navigateToDetailScreen(alarmItemUi: AlarmItemUi?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(selectedAlarmUiItem = alarmItemUi)
            navigator.navigate(
                destination = Destination.DetailScreen(alarmItemUi?.id)
            )
        }
    }

    fun navigateToTriggerScreen() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.TriggerGraph
            )
        }
    }

    fun onAction(action: AlarmListAction) {
        when (action) {
            is AlarmListAction.OnAlarmClicked -> {
                navigateToDetailScreen(action.alarmItemUi)
            }

            is AlarmListAction.OnAddAlarmClicked -> {
                navigateToDetailScreen(null)
            }

            is AlarmListAction.OnCheckChanged -> {
                viewModelScope.launch {
                    val alarmItemUi = action.alarmItemUi
                    val isChecked = action.isChecked
                    val alarmItem = alarmItemUi.toAlarmItem()
                    val updatedAlarmItem = alarmItem.copy(isEnabled = isChecked)
                    alarmsRepository.updateAlarm(updatedAlarmItem)
                }
            }
        }
    }
}