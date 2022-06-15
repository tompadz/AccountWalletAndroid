package com.xslite.sharemyaccountnative.ui.sheets

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.databinding.SheetSettingsThemeBinding
import com.xslite.sharemyaccountnative.ui.MainActivity
import com.xslite.sharemyaccountnative.ui.ScreenshotActivity
import com.xslite.sharemyaccountnative.util.AndroidUtil
import com.xslite.sharemyaccountnative.util.listeners.SheetSettingsThemeListener
import java.io.ByteArrayOutputStream


class SheetSettingsTheme(
    private val listener:SheetSettingsThemeListener
) : BottomSheetDialogFragment() {

    private var _binding: SheetSettingsThemeBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SheetSettingsTheme"
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = SheetSettingsThemeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.apply {
            val themeListener = View.OnClickListener { p0 ->
                when(p0?.id) {
                    buttonDark.id -> {
                        listener.onThemeChange(theme = AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    buttonLight.id -> {
                        listener.onThemeChange(theme = AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    buttonSystem.id -> {
                        listener.onThemeChange(theme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
                this@SheetSettingsTheme.dismiss()
                openScreenshotActivity()
            }
            buttonDark.setOnClickListener(themeListener)
            buttonLight.setOnClickListener(themeListener)
            buttonSystem.setOnClickListener(themeListener)
        }

        return view
    }

    /**
     * To start the theme change animation,
     * you need to take a screenshot of the main screen and then go to the page containing only the image.
     * In this function, we take a screenshot from MainActivity.kt, convert the screenshot into a ByteArray
     * to then pass to ScreenshotActivity.kt
     */
    private fun openScreenshotActivity(posX:Int? = null, posY:Int? = null) {

        val measuredWidth : Int = AndroidUtil().getDeviceWith()
        val measuredHeight : Int = AndroidUtil().getDeviceHeight()

        //We take a screenshot of the page and pull it out to further convert it into a byte array
        val bmp = (requireActivity() as MainActivity).takeScreenshot()
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes : ByteArray = stream.toByteArray()

        val intent = Intent(requireContext(), ScreenshotActivity::class.java).apply {
            //screen x/y added for the ability to change the position of the circle at the start of the animation
            putExtra("screenX", posX ?: measuredWidth / 2)
            putExtra("screenY", posY ?: measuredHeight / 2)
            putExtra("BMP", bytes)
            //We remove the animation so that the user does not notice the page change
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
        //We remove the animation so that the user does not notice the page change
        requireActivity().overridePendingTransition(0, 0)
        //Recreate the main page so that the theme of the application changes
        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}