package com.camgist.snoozeloo.di

import com.camgist.snoozeloo.alarm.data.AlarmsRepository
import com.camgist.snoozeloo.alarm.data.BaseAlarmsRepository
import com.camgist.snoozeloo.alarm.data.database.AlarmsDao
import com.camgist.snoozeloo.alarm.data.database.AlarmsDatabase
import com.camgist.snoozeloo.navigation.DefaultNavigator
import com.camgist.snoozeloo.navigation.Destination
import com.camgist.snoozeloo.navigation.Navigator
import com.camgist.snoozeloo.alarm.presentation.alarm_trigger.ViewModelAlarmTrigger
import com.camgist.snoozeloo.alarm.presentation.alarm_list.ViewModelAlarmList
import com.camgist.snoozeloo.alarm.presentation.alarm_details.ViewModelAlarmDetail
import com.camgist.snoozeloo.alarmManager.AlarmScheduler
import com.camgist.snoozeloo.alarmManager.AndroidAlarmScheduler
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    // TODO: I suspect this is causing the issue with multiple nav graphs as each has its
    //  own start destination, but DI is not sure how to provide it when used in other context
    single<Navigator> {
        DefaultNavigator(startDestination = Destination.HomeGraph)
    }

    // Database and DAO
    single {
        AlarmsDatabase.getAlarmsDatabase(context = get()).alarmsDao()
    }

    singleOf(::AlarmsRepository).bind<BaseAlarmsRepository>()

    single<AlarmScheduler> { AndroidAlarmScheduler(context = get()) }

    viewModelOf(::ViewModelAlarmTrigger)
    viewModelOf(::ViewModelAlarmList)
    viewModelOf(::ViewModelAlarmDetail)
}
