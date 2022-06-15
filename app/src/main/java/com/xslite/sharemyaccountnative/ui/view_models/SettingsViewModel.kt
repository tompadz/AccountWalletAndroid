package com.xslite.sharemyaccountnative.ui.view_models

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xslite.sharemyaccountnative.util.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs : SharedPrefs,
):ViewModel() {


    fun getPrefTheme():Int = prefs.getIntPref(SharedPrefs.PrefKey.DARK_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    fun getPrefDynamicColors():Boolean = prefs.getBooleanPref(SharedPrefs.PrefKey.DYNAMIC_COLORS)

    fun setPrefDynamicColors(value:Boolean, onSuccesses:() -> Unit, onError:() -> Unit){
        try {
            viewModelScope.launch (Dispatchers.Main) {
                val job : Job = launch(Dispatchers.IO) {
                    prefs.setPref(
                        type = SharedPrefs.PrefType.BOOL,
                        key = SharedPrefs.PrefKey.DYNAMIC_COLORS,
                        value = value
                    )
                }
                job.join()
                onSuccesses()
            }
        }catch (e:Exception){
            onError()
        }
    }

    fun setTheme(mode:Int, onSuccesses : () -> Unit){
        viewModelScope.launch (Dispatchers.Main) {
            val job:Job = launch (Dispatchers.IO) {
                prefs.setPref(
                    type = SharedPrefs.PrefType.INT,
                    key = SharedPrefs.PrefKey.DARK_THEME,
                    value = mode
                )
            }
            job.join()
            onSuccesses()
        }
    }

}