package com.camgist.snoozeloo.alarm.presentation.alarm_trigger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.R
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import com.camgist.snoozeloo.alarm.presentation.composables.MyRoundedButton
import com.camgist.snoozeloo.alarm.presentation.composables.previewAlarmItem
import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import com.camgist.snoozeloo.ui.theme.MyDimensions
import com.camgist.snoozeloo.ui.theme.SnoozelooTheme


@Preview
@Composable
fun PreviewAlarmTriggerScreen() {
    SnoozelooTheme {
        AlarmTriggerScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            onButtonClicked = {},
            alarmItem = previewAlarmItem
        )
    }
}

@Composable
fun RootAlarmTriggerScreen(vm: ViewModelAlarmTrigger, alarmItem: AlarmItemUi) {
    AlarmTriggerScreen(
        Modifier,
        vm::navigateBack,
        alarmItem
    )
}

@Composable
fun AlarmTriggerScreen(

    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit,
    alarmItem: AlarmItemUi
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.alarm),
                contentDescription = "Alarm icon",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary

            )
            Spacer(Modifier.size(MyDimensions.largePadding))

            Text(
                text = alarmItem.getFormattedTime(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.size(MyDimensions.largePadding))

            Text(
                text = alarmItem.alarmName ?: "",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 28.sp
                )
            )

            Spacer(Modifier.size(MyDimensions.largePadding))

            MyRoundedButton(
                buttonText = "Turn Off",
                modifier = Modifier.height(IntrinsicSize.Max),
                buttonBgColour = MaterialTheme.colorScheme.primary,
                paddingValues = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
            ) {
                onButtonClicked()
            }
        }
    }
}