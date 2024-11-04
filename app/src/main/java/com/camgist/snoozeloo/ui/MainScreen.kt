package com.camgist.snoozeloo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.ui.composables.AlarmItem
import com.camgist.snoozeloo.ui.theme.MyDimensions

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}

@Composable
fun RootMainScreen() {
    MainScreen()
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(MyDimensions.largePadding)
    ) {
        Text(
            text = "Your alarms",
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(MyDimensions.largePadding))
        AlarmItem()
    }
}

