package com.peshale.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.peshale.nmedia.post.Post
import com.peshale.nmedia.util.Util.counter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            published = "21 мая 2020",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        )

        val author = findViewById<TextView>(R.id.author)
        author.text = post.author
        val published = findViewById<TextView>(R.id.published)
        published.text = post.published
        val content = findViewById<TextView>(R.id.content)
        content.text = post.content

        val like = findViewById<ImageButton>(R.id.like)
        val likesTextView = findViewById<TextView>(R.id.likeCount)
        likesTextView.text = post.likes.toString()

        val sharesTextView = findViewById<TextView>(R.id.shareCount)
        sharesTextView.text = post.shares.toString()

        val viewsTextView = findViewById<TextView>(R.id.viewsCount)
        viewsTextView.text = post.views.toString()

        like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            like.setImageResource(
                if (post.likedByMe) R.drawable.baseline_favorite_red_500_24dp else R.drawable.baseline_favorite_border_black_24dp
            )
            if (post.likedByMe) ++post.likes else --post.likes
            likesTextView.text = post.likes.toString()
        }

        findViewById<ImageButton>(R.id.share).setOnClickListener {
            post.shares = ++post.shares
            counter(sharesTextView, post.shares)
        }

        findViewById<ImageButton>(R.id.views).setOnClickListener {
            post.views = ++post.views
            counter(viewsTextView, post.views)
        }
    }
}