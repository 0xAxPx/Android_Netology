package ru.netology.nmedia.util

import android.content.Context
import java.io.IOException

object Util {
    fun getJsonFromAssets(context: Context, fileName: String?): String? {
        val jsonString: String = try {
            val `is` = context.assets.open(fileName!!)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}