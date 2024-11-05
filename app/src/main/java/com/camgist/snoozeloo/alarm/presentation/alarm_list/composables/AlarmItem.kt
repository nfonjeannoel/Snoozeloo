package com.camgist.snoozeloo.alarm.presentation.alarm_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.ui.theme.MyDimensions

@Composable
@PreviewLightDark
fun PreviewAlarmItem() {
    AlarmItem()
}


@Composable
fun AlarmItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(MyDimensions.largePadding)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Wake up",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                style = MaterialTheme.typography.titleMedium
            )

            Switch(true, {})
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "10:00",
                modifier = Modifier,
//                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "AM",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = MyDimensions.smallPadding, bottom = MyDimensions.miniPadding)
            )
        }
        Text(
            text = "Alarm in 30 min",
//            color = MaterialTheme.colorScheme.outlineVariant,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = MyDimensions.smallPadding)
        )
    }
}