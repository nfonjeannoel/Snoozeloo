package com.camgist.snoozeloo.di

import com.camgist.snoozeloo.navigation.DefaultNavigator
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.ViewModelAlarmTrigger
import com.camgist.snoozeloo.alarm.presentation.alarm_list.ViewModelAlarmList
import com.camgist.snoozeloo.alarm.presentation.alarm_details.ViewModelAlarmDetail
import com.camgist.snoozeloo.alarmManager.AlarmScheduler
import com.camgist.snoozeloo.alarmManager.AndroidAlarmScheduler
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<Navigator> {
        DefaultNavigator(startDestination = Destination.HomeGraph)
    }

    single<AlarmScheduler> { AndroidAlarmScheduler(context = get()) }

    viewModelOf(::ViewModelAlarmTrigger)
    viewModelOf(::ViewModelAlarmList)
    viewModelOf(::ViewModelAlarmDetail)
}
