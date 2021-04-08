package com.example.citiboxchallenge

import android.app.Application
import com.example.citiboxchallenge.presentation.di.uiModule
import com.example.framework.di.dataSourceModule
import com.example.framework.di.dbModule
import com.example.framework.di.networkModule
import com.example.framework.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(dbModule, networkModule, dataSourceModule, repositoryModule, uiModule)
        }
    }
}