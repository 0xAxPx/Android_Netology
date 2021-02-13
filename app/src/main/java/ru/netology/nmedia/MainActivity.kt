package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.posts.PostData
import ru.netology.nmedia.util.Util

class MainActivity : AppCompatActivity() {

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonToString = Util.getJsonFromAssets(applicationContext, POSTS_FILE)
        if (jsonToString != null) {
            val posts: PostData = Gson().fromJson<PostData>(jsonToString)
            Log.i("Reading post data:", posts.toString())
            val data = posts.data.get(0)
            val author = findViewById<TextView>(R.id.author)
            author.text = data.author
            val published = findViewById<TextView>(R.id.published)
            published.text = data.published
        }
    }

    companion object {
        const val POSTS_FILE = "posts.json"
    }
}