package com.songnick.mincy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MincyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}