package com.camgist.snoozeloo.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SnoozelooApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SnoozelooApp)
            androidLogger()

            modules(appModule)
        }
    }
}