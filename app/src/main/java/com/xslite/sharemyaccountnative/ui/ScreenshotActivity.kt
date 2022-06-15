package com.xslite.sharemyaccountnative.ui

import android.animation.Animator
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.ViewAnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.xslite.sharemyaccountnative.databinding.ActivityScreenshotBinding
import com.xslite.sharemyaccountnative.util.AndroidUtil
import com.xslite.sharemyaccountnative.util.AppAnimatorListener
import com.xslite.sharemyaccountnative.util.CubicBezierInterpolator

/**
 * Page for theme change animation in the application,
 * The page accepts required arguments:
 * screenX animation horizontal position;
 * screenY animation position vertically;
 * BMP screenshot of the main page in ByteArray format
 *
 * The page itself consists of an image stretched to full screen.
 * When the page starts, we get all the arguments and set them,
 * then we start the animation that will close our image,
 * below the image we will see the already updated app theme
 */
class ScreenshotActivity : AppCompatActivity() {

    private lateinit var binding:ActivityScreenshotBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenshotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            //get args from main activity
            val posX = intent.getIntExtra("screenX", -1)
            val posY = intent.getIntExtra("screenY", -1)
            val bytes : ByteArray? = intent.getByteArrayExtra("BMP")

            //if the screenshot was not sent, just close the page
            if (bytes == null) finish()
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)

            container.setImageBitmap(bitmap)
            container.scaleType = ImageView.ScaleType.MATRIX
            container.isVisible = true

            val finalRadius = AndroidUtil().getDeviceHeight().toFloat()

            //start the animation to hide our image and show the new theme
            container.post {
                ViewAnimationUtils.createCircularReveal(binding.container, posX, posY, finalRadius, 0f).apply {
                    duration = 600
                    addListener(object : AppAnimatorListener() {
                        override fun onAnimationEnd(p0 : Animator?) {
                            container.setImageDrawable(null)
                            container.isVisible = false
                            finish()
                        }
                    })
                }.start()
            }
        }
    }
}