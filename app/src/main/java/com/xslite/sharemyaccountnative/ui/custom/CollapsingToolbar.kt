package com.xslite.sharemyaccountnative.ui.custom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapsingToolbar:CollapsingToolbarLayout {

    constructor(_context: Context):super(_context, null)
    constructor(context: Context, attrs: AttributeSet?) :super(context, attrs)

    fun setScrollEnable(state:Boolean) {
        if (state){
            val params = this.layoutParams as AppBarLayout.LayoutParams
            params.scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        }else {
            val params = this.layoutParams as AppBarLayout.LayoutParams
            params.scrollFlags = 0
        }
    }
}