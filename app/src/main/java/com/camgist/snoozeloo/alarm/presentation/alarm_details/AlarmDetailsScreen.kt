package com.camgist.snoozeloo.alarm.presentation.alarm_details

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.alarm.presentation.composables.AlarmTimeInput
import com.camgist.snoozeloo.alarm.presentation.composables.MyRoundedButton
import com.camgist.snoozeloo.ui.theme.MontserratFontFamily
import com.camgist.snoozeloo.ui.theme.MyDimensions
import com.camgist.snoozeloo.utils.getBottomWindowInsetDp
import com.camgist.snoozeloo.utils.rememberKeyboardVisibility


@Preview
@Composable
fun PreviewAlarmDetailsScreen() {
    AlarmDetailsScreen(
        state = AlarmDetailsState(showNameDialog = true),
        onAction = {},
    )
}


@Composable
fun AlarmDetailsScreen(
    state: AlarmDetailsState,
    onAction: (AlarmDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    
    val isKeyboardVisible by rememberKeyboardVisibility()

    // Handle back button press
    BackHandler(state.showNameDialog) {
        onAction(AlarmDetailsAction.OnDismissNameDialog) // Close the dialog
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer) // Todo: Replace for bacground color after palette
            .padding(MyDimensions.largePadding)
            .alpha(if (state.showNameDialog) 0.4f else 1f)
    ) {

        // Custom fun for better readability.
        TopUserOptionsRow(
            state = state,
            modifier = Modifier.height(IntrinsicSize.Max),
            onAction = onAction
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        NewAlarmDateBlock(
            state = state,
            modifier = Modifier,
            onAction = onAction
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        // Next alarm text
        if (!state.isTimeValid && state.errorMessage != null) {
            Text(
                text = state.errorMessage ?: "Invalid time",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
        } else if (state.isTimeValid) {
            Text(
                text = state.nextAlarmText,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                ),
            )
        } else {
            Text(
                text = "Please enter a valid time",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                ),
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        AlarmNameBlock(
            state = state,
            onAction = onAction,
            modifier = Modifier
        )

    }

    if (state.showNameDialog) {
        AlarmNameInputPopUp(
//            bottomInsetSize = bottomInsetSize,
            isKeyboardVisible = isKeyboardVisible,
            state = state,
            onAction = onAction,
            modifier = modifier
                .fillMaxSize()
                .padding(MyDimensions.largePadding)
        )
    }
}

@Composable
fun AlarmNameInputPopUp(
    isKeyboardVisible: Boolean,
//    bottomInsetSize: Dp,
    state: AlarmDetailsState,
    onAction: (AlarmDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current
    val focusRequester = remember { FocusRequester() }

    // Track text and cursor position
    var textFieldValue = remember {
        mutableStateOf(TextFieldValue(state.tempAlarmName ?: "", TextRange(state.tempAlarmName?.length ?: 0)))
    }

    // Ensure that focus is requested when the composable is first composed
    LaunchedEffect(Unit) {
        focusRequester.requestFocus() // This will show the keyboard
    }

    // Update textFieldValue when the alarm name changes
    LaunchedEffect(state.tempAlarmName) {
        textFieldValue.value = TextFieldValue(state.tempAlarmName ?: "", TextRange(state.tempAlarmName?.length ?: 0))
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(16.dp))
                .padding(MyDimensions.regularPadding),
        ) {
            Column {
                Text(
                    text = "Alarm Name", style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = MontserratFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = textFieldValue.value,
                    onValueChange = { newTextFieldValue ->
                        textFieldValue.value = newTextFieldValue
                        onAction(AlarmDetailsAction.OnAlarmNameChanged(newTextFieldValue.text))
                    },
                    placeholder = {
                        Text(
                            "Enter alarm name", style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = MontserratFontFamily,
                                fontWeight = FontWeight.Normal,
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp, horizontal = 0.dp)
                        .focusRequester(focusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,

                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                MyRoundedButton(
                    buttonText = "Save",
                    modifier = Modifier.align(Alignment.End),
                ) {
                    onAction(AlarmDetailsAction.OnSaveAlarmNameClicked)
                }
            }
        }
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .then(
                    if(isKeyboardVisible || isPreview) Modifier.imePadding() else Modifier
                )
        )
    }
}


@Composable
fun TopUserOptionsRow(
    state: AlarmDetailsState,
    modifier: Modifier = Modifier,
    onAction: (AlarmDetailsAction) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { onAction(AlarmDetailsAction.OnCloseClicked) }
                .fillMaxHeight() // Adapts to the parent height if set, else occupies all screen
                .aspectRatio(1f) // Make it square.
                .background(MaterialTheme.colorScheme.surfaceDim, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        // We use this "Spacer" to push the side elements to the sides with the weight modifier.
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        if (state.alarmItemUi != null){
            MyRoundedButton(
                buttonText = "Delete",
                onButtonClicked = { onAction(AlarmDetailsAction.OnDeleteClicked(state.alarmItemUi)) }
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
        }

        MyRoundedButton(
            buttonText = "Save",
            isEnabled = state.isTimeValid,
            onButtonClicked = { onAction(AlarmDetailsAction.OnSaveClicked) }
        )

    }
}


@Composable
fun NewAlarmDateBlock(
    state: AlarmDetailsState,
    modifier: Modifier = Modifier,
    onAction: (AlarmDetailsAction) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(16.dp))
            .padding(MyDimensions.largePadding)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)

        ) {
            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(vertical = MyDimensions.regularPadding)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AlarmTimeInput(
                    value = state.hours,
                    onValueChange = { newHour ->
                        Log.d("AlarmDetailsScreen", "New Hour: $newHour")
                        onAction(AlarmDetailsAction.OnHoursChanged(newHour))
                    },
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(MyDimensions.largePadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ":",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = MontserratFontFamily,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.outline
                    )
                )

            }

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(vertical = MyDimensions.regularPadding)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AlarmTimeInput(
                    value = state.minutes,
                    onValueChange = { newMinutes ->
                        onAction(AlarmDetailsAction.OnMinutesChanged(newMinutes))
                    },
                )
            }
        }
    }
}

@Composable
fun AlarmNameBlock(
    state: AlarmDetailsState,
    onAction: (AlarmDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(MyDimensions.regularPadding)
            .clickable { onAction(AlarmDetailsAction.OnAlarmNameClicked) },
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            text = "Alarm Name",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = state.alarmName ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.LightGray // Todo: Change Color with new palette.
            )
        )

    }
}
