package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.util.Util

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonToString = Util.readFileFromAssets(context = applicationContext, fileName = POSTS_FILE)
        if (jsonToString != null) {
            val posts = Util.getJsonDataByAuthor(json2String = jsonToString, author = AUTHOR)[0]
            val author = findViewById<TextView>(R.id.author)
            author.text = posts.author
            val published = findViewById<TextView>(R.id.published)
            published.text = posts.published
            val content = findViewById<TextView>(R.id.content)
            content.text = posts.content

            //temp setting count for likes, share and views
            val likesCount = findViewById<TextView>(R.id.likeCount)
            likesCount.text = "33"
            val shareCount = findViewById<TextView>(R.id.shareCount)
            shareCount.text = "44"
            val viewsCount = findViewById<TextView>(R.id.viewsCount)
            viewsCount.text = "111"

        }
    }

    companion object {
        const val POSTS_FILE = "posts.json"
        const val AUTHOR = "Нетология. Университет интернет-профессий"
    }
}