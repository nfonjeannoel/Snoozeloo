package com.camgist.snoozeloo.alarm.presentation.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.ui.theme.MontserratFontFamily
import com.camgist.snoozeloo.ui.theme.SnoozelooTheme

@Composable
fun AlarmTimeInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = MaterialTheme.typography.displayLarge.copy(
            fontFamily = MontserratFontFamily,
            fontSize = 52.sp,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun PreviewTimeInput() {
    SnoozelooTheme {
        AlarmTimeInput(value = "12", onValueChange = {})
    }
}