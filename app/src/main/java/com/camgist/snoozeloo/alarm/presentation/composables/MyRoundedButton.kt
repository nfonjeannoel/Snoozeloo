package com.camgist.snoozeloo.alarm.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
    buttonBgColour: Color = MaterialTheme.colorScheme.primary,
    buttonBgColourDisabled: Color = MaterialTheme.colorScheme.surfaceDim,
    buttonShape: Shape = RoundedCornerShape(100),
    isEnabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = MyDimensions.regularPadding,
        vertical = 6.dp
    ),
    onButtonClicked: () -> Unit = {},
) {

    val buttonColor = if (isEnabled) buttonBgColour else buttonBgColourDisabled

    BaseAppButton(
        modifier = modifier,
        buttonShape = buttonShape,
        buttonColor = buttonColor,
        paddingValues = paddingValues,
        isButtonEnabled = isEnabled,
        onClick = onButtonClicked
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
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
}

@Composable
fun BaseAppButton(
    modifier: Modifier = Modifier,
    buttonShape: Shape,
    buttonColor: Color,
    paddingValues: PaddingValues,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(buttonShape)
            .background(buttonColor)
            .clickable { if (isButtonEnabled) onClick() }
            .padding(paddingValues),
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewMyRoundedButton() {
    SnoozelooTheme {
        MyRoundedButton("Save")
    }
}