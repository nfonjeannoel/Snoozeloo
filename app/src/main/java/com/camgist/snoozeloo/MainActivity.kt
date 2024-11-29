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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.camgist.snoozeloo.alarm.domain.AlarmItem
import com.camgist.snoozeloo.alarm.presentation.alarm_details.AlarmDetailsScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_details.ViewModelAlarmDetail
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListAction
import com.camgist.snoozeloo.alarm.presentation.alarm_list.AlarmListScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_list.ViewModelAlarmList
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.RootAlarmTriggerScreen
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.ViewModelAlarmTrigger
import com.camgist.snoozeloo.alarm.presentation.models.toAlarmItemUi
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.NavigationAction
import com.camgist.snoozeloo.navigation.Navigator
import com.camgist.snoozeloo.ui.theme.MyDimensions
import com.camgist.snoozeloo.ui.theme.SnoozelooTheme
import kotlinx.serialization.json.Json
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
                val alarmTriggerViewModel = koinViewModel<ViewModelAlarmTrigger>()

                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination

//                LaunchedEffect(Unit) {
//                    val target = intent.getStringExtra("EXTRA_NAVIGATION_TARGET")
//                    Log.d("MainActivity", "Navigation target check: $target")
//                    if (target == "TriggerScreen") {
//                        navigator.navigate(Destination.TriggerScreen)
//                    }
//                }
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

                            is NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }


                    NavHost(
                        navController = navController,
                        startDestination = navigator.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Destination.HomeGraph>(
                            startDestination = when (intent.getStringExtra("EXTRA_NAVIGATION_TARGET")) {
                                "TriggerScreen" -> Destination.TriggerScreen
                                else -> Destination.HomeScreen
                            }
                        ) {
                            composable<Destination.HomeScreen> {
                                AlarmListScreen(
                                    state = alarmListState,
                                    onAction = alarmListViewModel::onAction
                                )
                            }

                            composable<Destination.DetailScreen> {
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

                            composable<Destination.TriggerScreen> {
                                val alarmExtra =
                                    intent.getStringExtra("EXTRA_ALARM") ?: return@composable
                                val alarmItem = Json.decodeFromString<AlarmItem>(alarmExtra)
                                RootAlarmTriggerScreen(
                                    alarmTriggerViewModel,
                                    alarmItem.toAlarmItemUi()
                                )
                            }
                        }

                    }


                }
            }
        }
    }
}

