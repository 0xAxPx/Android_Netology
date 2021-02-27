package ru.netology.nmedia.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.peshale.nmedia.MainActivity
import org.slf4j.LoggerFactory
import ru.netology.nmedia.posts.Data
import ru.netology.nmedia.posts.PostData
import java.io.IOException

object Util {

    val logger = LoggerFactory.getLogger(Util.javaClass)

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun readFileFromAssets(context: Context, fileName: String?): String? {
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

    fun getJsonDataByAuthor(json2String: String, author: String): List<Data> {
        val file = Gson().fromJson<PostData>(json2String)
        logger.info("Reading ${MainActivity.POSTS_FILE} from assets")
        val posts = file.data
        return posts.filter { it.author == author }
    }
}