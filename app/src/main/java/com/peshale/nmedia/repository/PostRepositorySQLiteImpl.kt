package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peshale.nmedia.dao.PostDao
import com.peshale.nmedia.dto.Post


class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun addPost(post: Post) {
        val id = post.id
        val saved = dao.addPost(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        dao.likedById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun toShareById(id: Long) {
        dao.toShareById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shares = it.shares + 1
            )
        }
        data.value = posts
    }

    override fun toViewById(id: Long) {
        dao.toViewById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                views = it.views + 1
            )
        }
        data.value = posts
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun findPostById(id: Long): Post {
        dao.findPostById(id)
        return posts.first { it.id == id }
    }
}