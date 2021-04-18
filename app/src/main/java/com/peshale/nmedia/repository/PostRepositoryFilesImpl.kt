package com.peshale.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.utils.AndroidUtils
import com.peshale.nmedia.utils.Arguments.FILE_POSTS
import com.peshale.nmedia.utils.Arguments.USER

class PostRepositoryFilesImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val file = context.filesDir.resolve(FILE_POSTS)
    private var posts = run {
        if (!file.exists()) return@run emptyList<Post>()
        file.readText()
            .ifBlank {
                return@run emptyList<Post>()
            }
            .let {
                gson.fromJson(it, type)
            }
    }
    var nextId = if (posts.isEmpty()) 1 else (posts.first().id + 1)

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun addPost(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = USER,
                    published = AndroidUtils.addLocalDataTime(),
                    likedByMe = false,
                    likes = 0,
                    shares = 0,
                    views = 0,
                )
            ) + posts
            update()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content, video = post.video)
        }
        update()
    }

    override fun deleteById(id: Long) {
        posts = posts.filter { it.id != id }
        update()
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) {
                it
            } else {
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
            }
        }
        update()
    }

    override fun toShareById(id: Long) {
        posts = posts.map {
            if (it.id == id) it.copy(shares = it.shares + 1) else it
        }
        update()
    }

    override fun toViewById(id: Long) {
        posts = posts.map {
            if (it.id == id) it.copy(views = it.views + 1) else it
        }
        update()
    }

    private fun sync() {
        context.openFileOutput(FILE_POSTS, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun findPostById(id: Long): Post {
        return posts.first { it.id == id }
    }

    private fun update() {
        data.value = posts
        sync()
    }
}