package com.xslite.sharemyaccountnative.util

import android.animation.Animator

abstract class AppAnimatorListener:Animator.AnimatorListener {
    override fun onAnimationCancel(p0 : Animator?) {
        //empty
    }
    override fun onAnimationEnd(p0 : Animator?) {
        //empty
    }
    override fun onAnimationRepeat(p0 : Animator?) {
        //empty
    }
    override fun onAnimationStart(p0 : Animator?) {
        //empty
    }
}