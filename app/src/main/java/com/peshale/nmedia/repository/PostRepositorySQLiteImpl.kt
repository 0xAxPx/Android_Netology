package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.peshale.nmedia.dao.PostDao
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.entity.PostEntity
import com.peshale.nmedia.entity.toPost


class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll(): LiveData<List<Post>> = dao.getAll().map {
        it.map(PostEntity::toPost)
    }

    override fun addPost(post: Post) {
        dao.addPost(PostEntity.fromPost(post))
    }

    override fun likeById(id: Long) {
        dao.likedById(id)
    }

    override fun toShareById(id: Long) {
        dao.toShareById(id)
    }

    override fun toViewById(id: Long) {
        dao.toViewById(id)
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun findPostById(id: Long): Post {
        return dao.findPostById(id)
    }
}