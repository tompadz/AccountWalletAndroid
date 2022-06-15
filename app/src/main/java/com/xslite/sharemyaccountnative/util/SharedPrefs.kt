package com.xslite.sharemyaccountnative.util

import android.content.Context
import javax.inject.Singleton

private const val SETTINGS_KEY = "SETTINGS"


@Singleton
class SharedPrefs (context:Context) {

    enum class PrefType {
        STRING,
        INT,
        BOOL
    }

    enum class PrefKey {
        DARK_THEME,
        DYNAMIC_COLORS
    }

    private val sharedPref = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)

    fun <T> setPref(type:PrefType, key:PrefKey, value:T){
        with (sharedPref.edit()) {
            when(type) {
                PrefType.STRING -> {
                    if (value !is String){
                        throw TypeCastException()
                    }
                    putString(key.name, value)
                }
                PrefType.INT -> {
                    if (value !is Int){
                        throw TypeCastException()
                    }
                    putInt(key.name, value)
                }
                PrefType.BOOL -> {
                    if (value !is Boolean){
                        throw TypeCastException()
                    }
                    putBoolean(key.name, value)
                }
            }
            apply()
        }
    }

    fun getBooleanPref(key : PrefKey):Boolean =
        sharedPref.getBoolean(key.name, false)

    fun getBooleanPref(key : PrefKey, defValue : Boolean):Boolean =
        sharedPref.getBoolean(key.name, defValue)

    fun getStringPref(key : PrefKey):String =
        sharedPref.getString(key.name, "") ?: ""

    fun getStringPref(key : PrefKey, defValue : String):String =
        sharedPref.getString(key.name, defValue) ?: defValue

    fun getIntPref(key : PrefKey):Int =
        sharedPref.getInt(key.name, 0)

    fun getIntPref(key : PrefKey, defValue:Int):Int =
        sharedPref.getInt(key.name, defValue)
}