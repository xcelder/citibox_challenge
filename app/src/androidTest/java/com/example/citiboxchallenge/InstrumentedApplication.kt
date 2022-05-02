package com.example.citiboxchallenge

import android.app.Application
import com.airbnb.mvrx.Mavericks

class InstrumentedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}