package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peshale.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        published = "21 мая 2020",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        likes = 999,
        share = 10999,
        views = 999999
    )

    private var data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) ++post.likes else --post.likes
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(share = post.share + 1)
        data.value = post
    }

    override fun view() {
        post = post.copy(views = post.views + 1)
        data.value = post
    }

    override fun counter(count: Int): String {
        println("Counter $count")
        return when {
            (count >= 1_000_000) -> "${"%.1f".format(count / 1_000_000.toDouble())}M"
            (count in 1000..9_999) -> "${"%.1f".format(count / 1_000.toDouble())}K"
            (count in 10_000..999_999) -> "${count / 1000}K"
            else -> count
        }.toString()
    }
}