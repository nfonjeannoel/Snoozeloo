package com.camgist.snoozeloo

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.camgist.snoozeloo.alarm.presentation.alarm_details.AlarmDetailsScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_details.AlarmDetailsState
import com.camgist.snoozeloo.alarm.presentation.alarm_details.ViewModelAlarmDetail
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListAction
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListState
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
                val alarmListViewModel = koinViewModel<ViewModelAlarmList>()
                val alarmListState by alarmListViewModel.state.collectAsStateWithLifecycle()
                val alarmDetailsViewModel = koinViewModel<ViewModelAlarmDetail>()
                val alarmDetailsState by alarmDetailsViewModel.state.collectAsStateWithLifecycle()
                val navController = rememberNavController()
                val navigator = koinInject<Navigator>()
//                    val alarmTriggerViewModel = koinViewModel<ViewModelAlarmTrigger>()

                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = MyDimensions.smallPadding),
                    floatingActionButton = {
                        // Only show FAB when current route is HomeScreen
                        if (currentDestination?.route?.contains(Destination.HomeScreen.toString()) == true) {
                            FloatingActionButton(
                                onClick = { alarmListViewModel.onAction(AlarmListAction.OnAddAlarmClicked) },
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                shape = CircleShape,
                            ) {
                                Icon(Icons.Filled.Add, "Add")
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                ) { innerPadding ->


                    ObserveAsEvents(flow = navigator.navigationActions) { action ->
                        when (action) {
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
                        navigation<Destination.HomeGraph>(
                            startDestination = Destination.HomeScreen
                        ) {
                            composable<Destination.HomeScreen> {
                                AlarmListScreen(
                                    state = alarmListState,
                                    onAction = alarmListViewModel::onAction
                                )
                            }

                            composable<Destination.DetailScreen> {
//                                AlarmDetailsScreen(
//                                    state =
//                                    if (alarmListState.selectedAlarmUiItem == null) {
//                                        // New alarm
////                                        AlarmDetailsState()
//                                        alarmDetailsState
//                                    } else {
//                                        // existing alarm. Modify the state to reflect the selected alarm
//                                        alarmDetailsViewModel.updateState(
//                                            alarmDetailsState.copy(
//                                                alarmItemUi = alarmListState.selectedAlarmUiItem,
//                                                hours = alarmListState.selectedAlarmUiItem?.hour.toString(),
//                                                minutes = alarmListState.selectedAlarmUiItem?.minute.toString(),
//                                                alarmName = alarmListState.selectedAlarmUiItem?.alarmName,
//                                                isTimeValid = true,
//                                                errorMessage = null
//                                            )
//                                        )
//                                        alarmDetailsState
//                                    },
//                                    onAction = alarmDetailsViewModel::onAction
//                                )

                                val args = it.toRoute<Destination.DetailScreen>()
                                LaunchedEffect(key1 = args.alarmItemId) {
                                    Log.d("MainActivity", "args.alarmItemId: ${args.alarmItemId}")
                                    alarmDetailsViewModel.initAlarmDetails(args.alarmItemId)
                                }
                                AlarmDetailsScreen(
                                    state = alarmDetailsState,
                                    onAction = alarmDetailsViewModel::onAction
                                )
                            }

//                            composable<Destination.TriggerScreen> {
//                                val viewModel = koinViewModel<ViewModelAlarmTrigger>()
//
//                                RootAlarmTriggerScreen(viewModel)
//                        }
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

