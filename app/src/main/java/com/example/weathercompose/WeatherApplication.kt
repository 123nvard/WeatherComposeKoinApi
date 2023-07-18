package com.example.weathercompose

import android.app.Application
import com.example.weathercompose.di.repoModule
import com.example.weathercompose.di.serviceModule
import com.example.weathercompose.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(applicationContext)
            modules(listOf(serviceModule, viewModelModule, repoModule))
        }
    }
}