package com.xslite.sharemyaccountnative.util

import android.content.Context
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.xslite.sharemyaccountnative.R
import org.json.JSONObject
import java.io.*

class LottieColorHelper {

    data class LottieColorSet(
        val layerName:String,
        @ColorInt val color:Int
    )

    @RequiresApi(Build.VERSION_CODES.S)
    fun getJsonDynamicColorTest(context : Context, colors:List<LottieColorSet>):String {

        val json = JSONObject(getJsonFromRes(R.raw.empty_animation,context))
        val layers = json.getJSONArray("layers")

        for (i in 0 until layers.length()) {

            val layer = layers[i] as JSONObject
            val shapes = layer.getJSONArray("shapes")

            for (j in 0 until shapes.length()){

                val shape = shapes[j] as JSONObject
                val it = shape.getJSONArray("it")
                val k = (it[1] as JSONObject).getJSONObject("c").getJSONArray("k")

                colors.map {
                    if (layer.getString("nm") == it.layerName){

                        for (g in 0 until k.length()) {
                            k.remove(0)
                        }

                        k.put(it.color.red / 255f)
                        k.put(it.color.green / 255f)
                        k.put(it.color.blue / 255f)
                        k.put(it.color.alpha / 255f)
                    }
                }
            }
        }

        return json.toString()
    }

    private fun getJsonFromRes(@RawRes res : Int, context:Context):String {
        val animRes : InputStream = context.resources.openRawResource(res)
        val writer : Writer = StringWriter()
        val buffer = CharArray(1024)
        animRes.use { raw ->
            val reader : Reader = BufferedReader(InputStreamReader(raw, "UTF-8"))
            var n : Int
            while (reader.read(buffer).also { n = it } != - 1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }

}