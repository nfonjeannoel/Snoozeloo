package com.camgist.snoozeloo.alarm.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.ui.theme.MyDimensions
import com.camgist.snoozeloo.ui.theme.SnoozelooTheme

@Composable
fun MyRoundedButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    buttonBgColour: Color = MaterialTheme.colorScheme.surfaceDim,
    onButtonClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onButtonClicked() }
            .background(
                buttonBgColour,
                shape = RoundedCornerShape(100)
            )
            .padding(horizontal = MyDimensions.regularPadding, vertical = 6.dp),

        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
    }
}


@Preview
@Composable
fun PreviewMyRoundedButton() {
    SnoozelooTheme {
        MyRoundedButton("Save")
    }
}