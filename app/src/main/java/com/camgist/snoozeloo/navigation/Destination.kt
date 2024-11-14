package com.camgist.snoozeloo.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    // Parent destinations
    @Serializable
    data object HomeGraph: Destination

    @Serializable
    data object DetailGraph: Destination

    @Serializable
    data object TriggerGraph: Destination

    // Child destinations
    @Serializable
    data object HomeScreen: Destination

    @Serializable
    data class DetailScreen(val id: String): Destination

    @Serializable
    data object TriggerScreen: Destination
}