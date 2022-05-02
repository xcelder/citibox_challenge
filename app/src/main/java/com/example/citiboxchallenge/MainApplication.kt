package com.example.citiboxchallenge

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.example.citiboxchallenge.presentation.di.viewModelModule
import com.example.framework.di.dataSourceModule
import com.example.framework.di.dbModule
import com.example.framework.di.networkModule
import com.example.framework.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Mavericks.initialize(this)

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(viewModelModule, dbModule, networkModule, dataSourceModule, repositoryModule)
        }
    }
}