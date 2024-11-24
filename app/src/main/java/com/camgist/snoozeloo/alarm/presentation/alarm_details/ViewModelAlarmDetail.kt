package com.camgist.snoozeloo.alarm.presentation.alarm_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.jarjarred.org.antlr.v4.runtime.atn.LexerActionExecutor.append
import com.camgist.snoozeloo.alarm.data.AlarmsRepository
import com.camgist.snoozeloo.alarm.data.BaseAlarmsRepository
import com.camgist.snoozeloo.alarm.data.database.toAlarmItem
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import com.camgist.snoozeloo.alarm.domain.utils.calculateNextAlarmText
import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import com.camgist.snoozeloo.alarm.presentation.models.toAlarmItem
import com.camgist.snoozeloo.alarm.presentation.models.toAlarmItemUi
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

class ViewModelAlarmDetail(
    private val navigator: Navigator,
    private val alarmsRepository: BaseAlarmsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AlarmDetailsState())
    val state = _state
        .onStart {
            // Load alarm details
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AlarmDetailsState()
        )


    fun updateState(state: AlarmDetailsState) {
        _state.value = state
    }

    fun onAction(action: AlarmDetailsAction) {
        when (action) {
            is AlarmDetailsAction.OnSaveClicked -> {
                // Save alarm to database
                saveAlarmToDatabase()
                navigateBack()
            }

            is AlarmDetailsAction.OnCloseClicked -> {
                navigateBack()
            }

            is AlarmDetailsAction.OnHoursChanged -> {
                if (action.hours.length <= 2 && action.hours.all { it.isDigit() }) {
                    updateTime(hours = action.hours, minutes = state.value.minutes)
                }
            }

            is AlarmDetailsAction.OnMinutesChanged -> {
                if (action.minutes.length <= 2 && action.minutes.all { it.isDigit() }) {
                    updateTime(hours = state.value.hours, minutes = action.minutes)
                }
            }

            AlarmDetailsAction.OnAlarmNameClicked -> {
                _state.value = _state.value.copy(
                    showNameDialog = true,
                    tempAlarmName = state.value.alarmName ?: ""
                )
            }

            is AlarmDetailsAction.OnAlarmNameChanged -> {
                _state.value = _state.value.copy(
                    tempAlarmName = action.alarmName
                )
            }

            AlarmDetailsAction.OnSaveAlarmNameClicked -> {
                _state.value = _state.value.copy(
                    alarmName = state.value.tempAlarmName,
                    showNameDialog = false
                )
            }

            AlarmDetailsAction.OnDismissNameDialog -> {
                _state.value = _state.value.copy(
                    showNameDialog = false
                )
            }

            is AlarmDetailsAction.OnDeleteClicked -> {
                deleteAlarmItem(action.alarmItemUi)
            }
        }
    }

    private fun deleteAlarmItem(alarmItemUi: AlarmItemUi) {
        viewModelScope.launch {
            alarmsRepository.deleteAlarm(alarmItemUi.toAlarmItem())
            navigator.navigateUp()
        }
    }

    private fun saveAlarmToDatabase() {
        viewModelScope.launch {
            if (state.value.alarmItemUi == null) {
                val newAlarmItem = AlarmItem(
                    hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    minute = Calendar.getInstance().get(Calendar.MINUTE),
                    alarmName = state.value.alarmName ?: "",
                    isEnabled = true,
                    id = 0
                )
                Log.d(
                    "ViewModelAlarmDetail",
                    "saveAlarmToDatabase: ${newAlarmItem.hour} ${newAlarmItem.minute}"
                )
                alarmsRepository.insertAlarm(newAlarmItem)
            } else {
                val oldAlarmItem = state.value.alarmItemUi!!.toAlarmItem()
                val updatedAlarmItem = oldAlarmItem.copy(
                    hour = state.value.hours.toInt(),
                    minute = state.value.minutes.toInt(),
                    alarmName = state.value.alarmName ?: "",
                )
                alarmsRepository.updateAlarm(updatedAlarmItem)
            }
        }
    }

    private fun updateTime(hours: String, minutes: String) {
        val isValid = validateTime(hours, minutes)
        _state.value = _state.value.copy(
            hours = hours,
            minutes = minutes,
            isTimeValid = isValid,
            errorMessage = if (!isValid && hours.isNotEmpty() && minutes.isNotEmpty()) {
                "Please enter a valid time between 00:00 and 23:59"
            } else null,
        )
        if (isValid) {
            updateNextAlarmText()
        }
    }

    private fun validateTime(hours: String, minutes: String): Boolean {
        if (hours.isEmpty() || minutes.isEmpty()) return false

        val hoursInt = hours.toIntOrNull() ?: return false
        val minutesInt = minutes.toIntOrNull() ?: return false

        return hoursInt in 0..23 && minutesInt in 0..59
    }

    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }

    fun initAlarmDetails(alarmItemId: Int?) {
        if (alarmItemId == null) {
            val currentTime = LocalTime.now()
            _state.value = AlarmDetailsState(
                hours = "${currentTime.hour}",
                minutes = "${currentTime.minute}",
                isTimeValid = true,
            )
            return
        }
        viewModelScope.launch {
            Log.d("ViewModelAlarmDetail", "initAlarmDetails: $alarmItemId")
            alarmsRepository.getAlarmStream(alarmItemId)
                .collect { alarmItem ->
                    if (alarmItem == null) {
                        // alarm not found
                        navigator.navigateUp()
                        return@collect
                    }

                    _state.value = alarmItem.let {
                        AlarmDetailsState(
                            alarmItemUi = it.toAlarmItemUi(),
                            hours = it.hour.toString(),
                            minutes = it.minute.toString(),
                            alarmName = it.alarmName,
                            isTimeValid = true,
                            errorMessage = null,
                        )
                    }
                    updateNextAlarmText()
                }
        }
    }

    private fun updateNextAlarmText() {
        val alarmTime = _state.value.toLocalTime()
        _state.value = _state.value.copy(
            nextAlarmText = calculateNextAlarmText(alarmTime)
        )
    }


}