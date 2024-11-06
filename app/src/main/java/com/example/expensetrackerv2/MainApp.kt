package com.example.expensetrackerv2

import android.app.Application
import com.github.pploszczyca.expensetrackerv2.telemetry.di.TelemetryDI
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Timber.d("Initializing Telemetry")
        TelemetryDI.init(log = Timber::d)
    }
}