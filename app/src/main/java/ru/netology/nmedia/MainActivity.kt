package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.util.Util

class MainActivity : AppCompatActivity() {

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonToString = Util.readFileFromAssets(context = applicationContext, fileName = POSTS_FILE)
        if (jsonToString != null) {
            val posts = Util.getJsonDataByAuthor(json2String = jsonToString, author = AUTHOR)[0]
//            val posts: PostData = Gson().fromJson<PostData>(jsonToString)
//            logger.info("Reading $POSTS_FILE from assets")
//            val data = posts.data.get(0)
            val author = findViewById<TextView>(R.id.author)
            author.text = posts.author
            val published = findViewById<TextView>(R.id.published)
            published.text = posts.published
            val content = findViewById<TextView>(R.id.content)
            content.setText(posts.content)
        }
    }

    companion object {
        const val POSTS_FILE = "posts.json"
        const val AUTHOR = "Нетология. Университет интернет-профессий"
    }
}