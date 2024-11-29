package com.camgist.snoozeloo.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/**
 * This function returns the Dp value of any -bottom-
 * window insets that come from Android, like the keyboard...
 */


// Thought I might need this, but i didn't... Still useful to know
@Composable
fun getBottomWindowInsetDp(): Dp {
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current

    // Check if the keyboard is visible by checking if `bottom` is greater than 0
    return with(density) { imeInsets.getBottom(density).toDp() }
}