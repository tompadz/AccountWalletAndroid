package com.xslite.sharemyaccountnative.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.dynamicColorEnable
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.pxFromDp

@SuppressLint("AppCompatCustomView")
class QrCodeImage:ImageView {

    constructor(_context: Context):super(_context, null)
    constructor(context: Context, attrs: AttributeSet?) :super(context, attrs)

    @SuppressLint("PrivateResource")
    private fun getColor():List<Int>{
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> listOf(Color.WHITE, context.getColor(R.color.dark_background2))
            else -> {
                if (context.dynamicColorEnable()) {
                    listOf(context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary20), Color.TRANSPARENT)
                } else {
                    listOf(context.getColor(R.color.dark_background), Color.TRANSPARENT)
                }
            }
        }
    }

    /**
     * The method generates a Qr code and immediately sets it to an image
     * The Zxing library is used to generate the code.
     * https://github.com/zxing/zxing
     * @param content is the content to put in the qr code
     */
    fun generateQrCode(content:String){
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix : BitMatrix = qrCodeWriter.encode(
            content,
            BarcodeFormat.QR_CODE,
            200f.pxFromDp(context),
            200f.pxFromDp(context)
        )
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)
        val colors = getColor()
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (bitMatrix[x, y]) colors[0] else colors[1]
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        this.setImageBitmap(bitmap)
    }
}