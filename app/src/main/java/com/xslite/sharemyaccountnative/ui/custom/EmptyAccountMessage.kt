package com.xslite.sharemyaccountnative.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.dynamicColorEnable
import com.xslite.sharemyaccountnative.util.LottieColorHelper

/**
 * Message with lottie animation, displayed on the main page if the list of accounts is empty,
 * s can change according to the user's color scheme if his android version is higher than 12 and permission is granted in the settings
 * @seeLottieColorHelper
 */
class EmptyAccountMessage : FrameLayout {

    private lateinit var lottie:LottieAnimationView

    constructor(_context : Context) : super(_context, null) {
        LayoutInflater.from(_context).inflate(R.layout.widget_empty_account_message, this)
    }

    constructor(context : Context, attrs : AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_empty_account_message, this)
    }


    /**
     * We set the color scheme for each layer from json lottie.
     * each layer will have its own color attached to it, which will need to be replaced in json
     * @see LottieColorHelper.LottieColorSet,
     */
    @SuppressLint("PrivateResource")
    private fun getDynamicColorSet():List<LottieColorHelper.LottieColorSet> {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
            return listOf()
        else
            return listOf(
                LottieColorHelper.LottieColorSet(
                    layerName = "bcg-2 Outlines",
                    color = context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_neutral_variant99)
                ),
                LottieColorHelper.LottieColorSet(
                    layerName = "bcg-1 Outlines",
                    color = context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_neutral95)
                ),
                LottieColorHelper.LottieColorSet(
                    layerName = "rond-scaleout-bubble Outlines",
                    color = context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary60)
                ),
                LottieColorHelper.LottieColorSet(
                    layerName = "square-45 Outlines",
                    color = context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary60)
                ),
                LottieColorHelper.LottieColorSet(
                    layerName = "square Outlines",
                    color = context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary60)
                ),
                LottieColorHelper.LottieColorSet(
                    layerName = "round-scaleout-slow Outlines",
                    color = context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary60)
                )
            )
    }

    fun addCircleClickListener(listener: OnClickListener) {
        lottie.setOnClickListener(listener)
    }

    fun removeAllClickListener(){
        lottie.setOnClickListener(null)
    }

    @SuppressLint("NewApi")
    override fun onFinishInflate() {
        super.onFinishInflate()

        val ln = findViewById<LinearLayout>(R.id.linearLayout)

        /**
         * Lottie
         */
        lottie = LottieAnimationView(context)
        lottie.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            600
        )
        lottie.loop(true)


        //Check current app theme
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                lottie.setAnimation(R.raw.empty_animation_dark)
            }
            else -> {
                //Check if you have access to install a dynamic color theme
                //dynamicColorEnable can be found in AndroidUtil.kt
                if (context.dynamicColorEnable()) {
                    //Setting dynamic colors with LottieColorHaler.kt
                    val json = LottieColorHelper().getJsonDynamicColorTest(
                        context = context,
                        colors = getDynamicColorSet()
                    )
                    lottie.setAnimationFromJson(json)
                } else {
                    lottie.setAnimation(R.raw.empty_animation)
                }
            }
        }
        lottie.playAnimation()
        ln.addView(lottie)
    }
}