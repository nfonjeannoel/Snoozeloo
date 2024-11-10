package com.camgist.snoozeloo.di

import com.camgist.snoozeloo.navigation.DefaultNavigator
import com.camgist.snoozeloo.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindNavigator(appNavigator: DefaultNavigator): Navigator
}