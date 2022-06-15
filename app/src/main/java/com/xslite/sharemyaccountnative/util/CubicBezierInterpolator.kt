package com.xslite.sharemyaccountnative.util

import android.R.attr
import android.graphics.PointF
import android.view.animation.Interpolator


//https://github.com/DrKLO/Telegram/blob/master/TMessagesProj/src/main/java/org/telegram/ui/Components/CubicBezierInterpolator.java

open class CubicBezierInterpolator : Interpolator {

    companion object {
        val DEFAULT : CubicBezierInterpolator = CubicBezierInterpolator(0.25f, 0.1f, 0.25f, 1f)
        val EASE_OUT : CubicBezierInterpolator = CubicBezierInterpolator(0f, 0f, .58f, 1f)
        val EASE_OUT_QUINT : CubicBezierInterpolator = CubicBezierInterpolator(.23f, 1f, .32f, 1f)
        val EASE_IN : CubicBezierInterpolator = CubicBezierInterpolator(.42f, 0f, 1f, 1f)
        val EASE_BOTH : CubicBezierInterpolator = CubicBezierInterpolator(.42f, 0f, .58f, 1f)
        val EASE_IN_OUT_QUAD = CubicBezierInterpolator(0.455, 0.03, 0.515, 0.955)
    }

    private var start : PointF? = null
    private var end : PointF? = null
    private var a = PointF()
    private var b = PointF()
    private var c = PointF()

    constructor(start : PointF, end : PointF) {
//        require(! (start.x < 0 || start.x > 1)) { "startX value must be in the range [0, 1]" }
//        require(! (end.x < 0 || end.x > 1)) { "endX value must be in the range [0, 1]" }
        this.start = start
        this.end = end
    }

    constructor(startX : Float, startY : Float, endX : Float, endY : Float) :
        this(
            PointF(attr.startX.toFloat(), attr.startY.toFloat()), PointF(
                attr.endX.toFloat(),
                attr.endY.toFloat()
            )
        )

    constructor(startX : Double, startY : Double, endX : Double, endY : Double) :
        this(
            attr.startX.toFloat(), attr.startY.toFloat(), attr.endX.toFloat(),
            attr.endY.toFloat()
        )


    override fun getInterpolation(time : Float) : Float {
        return getBezierCoordinateY(getXForTime(time))
    }

    private fun getBezierCoordinateY(time : Float) : Float {
        c.y = 3 * start !!.y
        b.y = 3 * (end !!.y - start !!.y) - c.y
        a.y = 1 - c.y - b.y
        return time * (c.y + time * (b.y + time * a.y))
    }

    private fun getXForTime(time : Float) : Float {
        var x = time
        var z : Float
        for (i in 1..13) {
            z = getBezierCoordinateX(x) - time
            if (Math.abs(z) < 1e-3) {
                break
            }
            x -= z / getXDerivate(x)
        }
        return x
    }

    private fun getXDerivate(t : Float) : Float {
        return c.x + t * (2 * b.x + 3 * a.x * t)
    }

    private fun getBezierCoordinateX(time : Float) : Float {
        c.x = 3 * start !!.x
        b.x = 3 * (end !!.x - start !!.x) - c.x
        a.x = 1 - c.x - b.x
        return time * (c.x + time * (b.x + time * a.x))
    }
}