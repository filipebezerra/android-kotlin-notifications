package dev.filipebezerra.android.eggtimernotifications

import android.app.Application
import timber.log.Timber

class EggTimerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BuildConfig.DEBUG.takeIf { it }?.run { Timber.plant(Timber.DebugTree()) }
    }
}