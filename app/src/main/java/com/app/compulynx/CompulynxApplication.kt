package com.app.compulynx

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CompulynxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}