package com.maxciv.rssreader

import android.app.Application
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
