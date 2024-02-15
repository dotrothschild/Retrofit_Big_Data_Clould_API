package com.inzhood.retrofitbigcloud

import android.app.Application

// Need this for HomeFragment. Could use context, but I will eventually use this for something
class BdcApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any application-wide configurations or services here
    }
}