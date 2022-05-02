package com.example.citiboxchallenge.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.citiboxchallenge.InstrumentedApplication

class CustomRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, InstrumentedApplication::class.java.name, context)
    }
}