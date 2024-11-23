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
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.camgist.snoozeloo.alarm.presentation.alarm_details.AlarmDetailsScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_details.RootAlarmDetailScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_details.ViewModelAlarmDetail
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListState
import com.camgist.snoozeloo.alarm.presentation.alarm_list.RootAlarmListScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_list.ViewModelAlarmList
import com.camgist.snoozeloo.alarm.presentation.alarm_list.previewAlarmListUi
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.AlarmTriggerScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.RootAlarmTriggerScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.ViewModelAlarmTrigger
import com.camgist.snoozeloo.alarm.presentation.composables.previewAlarmItem
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.NavigationAction
import com.camgist.snoozeloo.navigation.Navigator
import com.camgist.snoozeloo.ui.theme.MyDimensions
import com.camgist.snoozeloo.ui.theme.SnoozelooTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

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

                    val navController = rememberNavController()
                    val navigator = koinInject<Navigator>()

                    ObserveAsEvents(flow = navigator.navigationActions) { action ->
                        when(action) {
                            is NavigationAction.Navigate -> navController.navigate(
                                action.destination
                            ) {
                                action.navOptions(this)
                            }
                            NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = navigator.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Destination.HomeGraph> (
                            startDestination = Destination.HomeScreen
                        ) {
                            composable<Destination.HomeScreen> {
                                val viewModel = koinViewModel<ViewModelAlarmList>()

                                RootAlarmListScreen(viewModel)
                            }

                            composable<Destination.DetailScreen> {
                                val viewModel = koinViewModel<ViewModelAlarmDetail>()
                                val args = it.toRoute<Destination.DetailScreen>()

                                RootAlarmDetailScreen(viewModel, args.id)
                            }

                            composable<Destination.TriggerScreen> {
                                val viewModel = koinViewModel<ViewModelAlarmTrigger>()

                                RootAlarmTriggerScreen(viewModel)
                            }
                        }
//                        navigation<Destination.DetailGraph> (
//                            startDestination = Destination.DetailScreen
//                        ) {
//                            composable<Destination.DetailScreen> {
//                                val viewModel = koinViewModel<ViewModelAlarmDetail>()
//                                val args = it.toRoute<Destination.DetailScreen>()
//
//                                RootAlarmDetailScreen(viewModel, args.id)
//                            }
//                        }
//
//                        navigation<Destination.TriggerGraph> (
//                            startDestination = Destination.TriggerScreen
//                        ) {
//                            composable<Destination.TriggerScreen> {
//                                val viewModel = koinViewModel<ViewModelAlarmTrigger>()
//
//                                RootAlarmTriggerScreen(viewModel)
//                            }
//                        }
                    }
                }
            }
        }
    }
}

