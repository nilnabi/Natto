package com.nilnabi.android.natto

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by nilnabi on 2016/09/03.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseAnalytics.getInstance(this)
    }

}