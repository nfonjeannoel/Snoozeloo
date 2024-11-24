package com.camgist.snoozeloo.alarm.presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import com.camgist.snoozeloo.alarm.domain.utils.calculateNextAlarmText
import com.camgist.snoozeloo.alarm.presentation.alarm_details.AlarmDetailsAction
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListAction
import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import com.camgist.snoozeloo.alarm.presentation.models.toAlarmItemUi
import com.camgist.snoozeloo.ui.theme.MyDimensions
import java.util.UUID

@Composable
@Preview
fun PreviewAlarmItem() {
    AlarmItem(previewAlarmItem, onAction = {})
}


@Composable
fun AlarmItem(
    alarmItemUi: AlarmItemUi,
    onAction: (AlarmListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
//                onItemClicked(
//                    UUID
//                        .randomUUID()
//                        .toString()
//                )
                onAction(AlarmListAction.OnAlarmClicked(alarmItemUi))

            } // Todo: Need to make sure it doesn't affect the switch button.
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(MyDimensions.largePadding)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alarmItemUi.alarmName ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            CustomLargeSwitch(checked = alarmItemUi.isEnabled, onCheckedChange = { checked ->
                onAction(AlarmListAction.OnCheckChanged(alarmItemUi, checked))
            }, scale = 0.9f)
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = alarmItemUi.getFormattedTime(),
                modifier = Modifier,
//                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = alarmItemUi.getAmPm(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(
                    start = MyDimensions.smallPadding,
                    bottom = MyDimensions.miniPadding
                )
            )
        }
        Text(
            text = calculateNextAlarmText(alarmItemUi.hour, alarmItemUi.minute),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = MyDimensions.smallPadding)
        )
    }
}

internal val previewAlarmItem = AlarmItem(
    id = 1,
    hour = 10,
    minute = 0,
    isEnabled = true,
    alarmName = "Wake up now"
).toAlarmItemUi()
