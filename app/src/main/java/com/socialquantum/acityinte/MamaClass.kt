package com.socialquantum.acityinte

import android.app.Application

import com.my.tracker.MyTracker
import com.onesignal.OneSignal


class MamaClass: Application() {

    companion object {
        var pampam: HashMap<String, String> = HashMap()
    }

    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId("8280201a-107d-4d29-9b81-a6b9a89e6ebf");
        val trackerConfig = MyTracker.getTrackerConfig()
        trackerConfig.isTrackingLaunchEnabled = true
        MyTracker.initTracker("76508847901597427684", this)
    }
}

