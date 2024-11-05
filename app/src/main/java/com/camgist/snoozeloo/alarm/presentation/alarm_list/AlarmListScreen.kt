package com.camgist.snoozeloo.alarm.presentation.alarm_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.alarm.presentation.alarm_list.composables.AlarmItem
import com.camgist.snoozeloo.ui.theme.MyDimensions

@PreviewLightDark
@Composable
fun PreviewMainScreen() {
    AlarmListScreen()
}


@Composable
fun AlarmListScreen(modifier: Modifier = Modifier) {
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
//        AlarmItem()
        LazyColumn {
            items(10) {
                AlarmItem()
                Spacer(modifier =Modifier.size(MyDimensions.regularPadding))
            }
        }
    }
}

