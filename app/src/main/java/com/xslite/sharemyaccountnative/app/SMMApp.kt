package com.xslite.sharemyaccountnative.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import com.xslite.sharemyaccountnative.util.SharedPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SMMApp : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * We check the theme saved in the settings and set it as the main one
         */

        val prefs = SharedPrefs(context = applicationContext)
        if (prefs.getBooleanPref(SharedPrefs.PrefKey.DYNAMIC_COLORS, false)){
            DynamicColors.applyToActivitiesIfAvailable(this)
        }
        val mode = prefs.getIntPref(SharedPrefs.PrefKey.DARK_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}