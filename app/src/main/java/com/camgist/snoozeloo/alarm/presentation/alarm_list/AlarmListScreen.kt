package com.camgist.snoozeloo.alarm.presentation.alarm_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.R
import com.camgist.snoozeloo.alarm.presentation.composables.AlarmItem
import com.camgist.snoozeloo.alarm.presentation.composables.previewAlarmItem
import com.camgist.snoozeloo.alarm.presentation.models.AlarmItemUi
import com.camgist.snoozeloo.ui.theme.MyDimensions

@Preview
@Composable
fun PreviewMainScreen() {
    AlarmListScreen(
        AlarmListState(
            alarmUiItems = previewEmptyAlarmListUi
        )
    )
}


@Composable
fun AlarmListScreen(
    state: AlarmListState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MyDimensions.largePadding)
    ) {
        Text(
            text = "Your alarms",
            fontSize = 24.sp,
            style = MaterialTheme.typography.displayLarge

        )

        Spacer(modifier = Modifier.height(MyDimensions.largePadding))
        if (state.alarmUiItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarm),
                        contentDescription = "Alarm Icon",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(MyDimensions.largePadding))
                    Text(
                        text = stringResource(R.string.empty_alarm_message),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn {
                items(state.alarmUiItems) { alarmItemUi ->
                    AlarmItem(alarmItemUi)
                    Spacer(modifier = Modifier.size(MyDimensions.regularPadding))
                }
            }
        }
    }
}

internal val previewAlarmListUi = (0..10).map { previewAlarmItem.copy(id = it) }
internal val previewEmptyAlarmListUi = emptyList<AlarmItemUi>()
