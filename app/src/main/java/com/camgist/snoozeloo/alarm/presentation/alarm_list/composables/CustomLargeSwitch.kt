package com.camgist.snoozeloo.alarm.presentation.alarm_list.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A customizable Material 3 style switch component with dynamic sizing capabilities.
 *
 * This switch component follows Material Design principles while allowing for size customization
 * through a scale parameter. The switch maintains proper proportions regardless of size.
 *
 * Features:
 * - Dynamic sizing while maintaining proportions
 * - Material 3 color scheme integration
 * - Animated thumb movement
 * - Customizable through modifier parameter
 *
 * @param checked The current state of the switch (true for on, false for off)
 * @param onCheckedChange Callback invoked when the switch is toggled, with the new state as parameter
 * @param modifier Modifier for the switch container, allowing for customization of layout, padding, etc.
 * @param scale Scale factor for the switch size. 1f is the default size, larger values increase size proportionally
 *
 * Example usage:
 * ```
 * var isChecked by remember { mutableStateOf(false) }
 * CustomLargeSwitch(
 *     checked = isChecked,
 *     onCheckedChange = { isChecked = it },
 *     scale = 1.2f // 20% larger than default
 * )
 * ```
 */
@Composable
fun CustomLargeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    scale: Float = 1f // Added scale parameter for customization
) {
    // Base measurements
    val baseTrackHeight = 36.dp
    val baseTrackWidth = 60.dp
    val baseThumbPadding = 2.dp
    val baseCornerRadius = 20.dp

    // Scaled measurements
    val trackHeight = (baseTrackHeight * scale)
    val trackWidth = (baseTrackWidth * scale)
    val thumbSize = (trackHeight * 0.89f) // Thumb is ~89% of track height
    val thumbPadding = (baseThumbPadding * scale)
    val cornerRadius = (baseCornerRadius * scale)

    // Calculate thumb offset
    val thumbOffset = trackWidth - thumbSize - thumbPadding

    Box {
        Box(
            modifier = modifier
                .width(trackWidth)
                .height(trackHeight)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    if (checked) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
                )
                .clickable { onCheckedChange(!checked) },
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(thumbSize)
                    .offset(x = if (checked) thumbOffset else thumbPadding)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                    .animateContentSize()
            )
        }
    }
}

@Preview
@Composable
fun PreviewCustomLargeSwitch() {
    CustomLargeSwitch(checked = true, onCheckedChange = {})
}

@Preview
@Composable
fun PreviewCustomLargeSwitchUnchecked() {
    CustomLargeSwitch(checked = false, onCheckedChange = {})
}