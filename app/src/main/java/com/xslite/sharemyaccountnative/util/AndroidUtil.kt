package com.xslite.sharemyaccountnative.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

private const val TAG = "ANDROID_UTIL"

class AndroidUtil {

    companion object {

        fun Float.pxFromDp(context : Context) : Int {
            return (this * context.resources.displayMetrics.density).toInt()
        }

        fun Float.dpFromPx(context : Context) : Int {
            return (this / context.resources.displayMetrics.density).toInt()
        }

        fun Context.copyToClipboard(text : CharSequence) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
        }

        fun Context.shareLink(link : String) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(Intent.createChooser(shareIntent, ""))
        }

        fun Context.openLink(link : String) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)
        }

        fun Context.dynamicColorEnable() : Boolean {
            val prefs = SharedPrefs(this)
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && prefs.getBooleanPref(SharedPrefs.PrefKey.DYNAMIC_COLORS)
        }

        fun Context.getStatusBarHeight(): Int {
            var result = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }

    fun getDeviceWith():Int = Resources.getSystem().displayMetrics.widthPixels
    fun getDeviceHeight():Int = Resources.getSystem().displayMetrics.heightPixels

    fun showKeyboard(view : View?) : Boolean {
        if (view == null) {
            return false
        }
        try {
            view.requestFocus()
            val inputManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e : Exception) {
            Log.e(TAG, e.message ?: "error")
        }
        return false
    }

    fun hideKeyboard(view : View?) {
        if (view == null) {
            return
        }
        try {
            view.clearFocus()
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (! imm.isActive) {
                return
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e : java.lang.Exception) {
            Log.e(TAG, e.message ?: "error")
        }
    }


}