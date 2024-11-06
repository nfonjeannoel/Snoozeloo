package com.camgist.snoozeloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_details.AlarmDetailsScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListScreen
import com.camgist.snoozeloo.ui.theme.MyDimensions
import com.camgist.snoozeloo.ui.theme.SnoozelooTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
//            .setKeepOnScreenCondition() {
//            // keep on forever for debugging purposes
//            true
//        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnoozelooTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = MyDimensions.smallPadding),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,

                        ) {
                            Icon(Icons.Filled.Add, "Add")
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { innerPadding ->
                    AlarmListScreen(modifier = Modifier.padding(innerPadding))
//                    AlarmDetailsScreen(modifier = Modifier.padding(innerPadding)) {}

                }

            }
        }
    }
}

