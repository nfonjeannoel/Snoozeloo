package com.camgist.snoozeloo.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object HomeScreen: Destination

    @Serializable
    data object DetailScreen: Destination
}