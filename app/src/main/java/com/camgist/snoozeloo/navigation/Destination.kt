package com.camgist.snoozeloo.navigation

import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import kotlinx.serialization.Serializable

sealed class Destination {

    // Parent destinations
    @Serializable
    data object HomeGraph : Destination()

//    @Serializable
//    data object DetailGraph : Destination()
//
//    @Serializable
//    data object TriggerGraph : Destination()

    // Child destinations
    @Serializable
    data object HomeScreen : Destination()

    @Serializable
    data class DetailScreen(val alarmItemId: Int?) : Destination()

    @Serializable
    data object TriggerScreen : Destination()
}