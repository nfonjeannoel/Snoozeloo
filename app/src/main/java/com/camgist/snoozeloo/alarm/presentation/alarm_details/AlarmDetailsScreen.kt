package com.camgist.snoozeloo.alarm.presentation.alarm_details

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camgist.snoozeloo.ui.theme.MontserratFontFamily
import com.camgist.snoozeloo.ui.theme.MyDimensions


@PreviewLightDark
@Composable
fun PreviewAlarmDetailsScreen() {
    AlarmDetailsScreen(Modifier) {}
}

@Composable
fun RootAlarmDetailScreen() {
    AlarmDetailsScreen(modifier = Modifier) {}
}

@Composable
fun AlarmDetailsScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer) // Todo: Replace for bacground color after palette
            .padding(MyDimensions.largePadding)
    ) {

        // Custom fun for better readability.
        TopUserOptionsRow(
            modifier = Modifier.height(32.dp)
        ) {

        }

        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))

        NewAlarmDateBlock(
            modifier = Modifier
        ) {

        }

        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))

        AlarmNameBlock(
            modifier = Modifier,
        ) {


        }
    }
}

@Composable
fun TopUserOptionsRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { onClick() }
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
        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(100)
                )
                .padding(horizontal = MyDimensions.regularPadding, vertical = 6.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "Save",
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
fun NewAlarmDateBlock(
    modifier: Modifier = Modifier,
    onValueChanged: () -> Unit
) {
    Box(
        modifier = Modifier
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
                    .background(MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.medium)
                    .padding(vertical = MyDimensions.regularPadding)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "00",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = MontserratFontFamily,
                        fontSize = 52.sp,
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(MyDimensions.largePadding),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
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
                    .background(MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.medium)
                    .padding(vertical = MyDimensions.regularPadding)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "00",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = MontserratFontFamily,
                        fontSize = 52.sp,
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

    }
}

@Composable
fun AlarmNameBlock(
    modifier: Modifier = Modifier,
    onUserInteraction: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(MyDimensions.regularPadding),
        verticalAlignment = Alignment.CenterVertically
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
            text = "Work",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = MontserratFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.LightGray // Todo: Change Color with new palette.
            )
        )

    }
}