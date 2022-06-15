package com.xslite.sharemyaccountnative.ui

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.DynamicColors
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.databinding.ActivityMainBinding
import com.xslite.sharemyaccountnative.util.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHost.findNavController()
    }

    fun takeScreenshot():Bitmap{
        val measuredWidth : Int = Resources.getSystem().displayMetrics.widthPixels
        val measuredHeight : Int = Resources.getSystem().displayMetrics.heightPixels

        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding.root.draw(canvas)

        return bitmap
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}