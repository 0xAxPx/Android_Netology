package com.peshale.nmedia.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.peshale.nmedia.db.AppDb
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.repository.PostRepository
import com.peshale.nmedia.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    likes = 0,
    shares = 0,
    views = 0,
    video = ""
)


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun addPost() {
        edited.value?.let {
            repository.addPost(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String, videoLink: String) {
        val text = content.trim()
        val link = videoLink.trim()
        if (edited.value?.content == text && edited.value?.video == link) {
            return
        }
        edited.value = edited.value?.copy(content = text, video = link)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun toShareById(id: Long) = repository.toShareById(id)
    fun toViewById(id: Long) = repository.toViewById(id)
    fun deleteById(id: Long) = repository.deleteById(id)

    fun searchPost(id: Long): Post {
        return repository.findPostById(id)
    }

    fun searchAndChangePost(id: Long) {
        val thisPost = repository.findPostById(id)
        val editedPost =
            thisPost.copy(content = edited.value?.content.toString(), video = edited.value?.video)
        repository.addPost(editedPost)
    }
}