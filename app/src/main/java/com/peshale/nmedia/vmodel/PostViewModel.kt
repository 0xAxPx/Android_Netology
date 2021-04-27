package com.peshale.nmedia.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.peshale.nmedia.db.AppDb
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.repository.PostRepository
import com.peshale.nmedia.repository.PostRepositorySQLiteImpl
import com.peshale.nmedia.utils.AndroidUtils

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = AndroidUtils.addLocalDataTime(),
    edited = "",
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

    fun changeContent(content: String, videoLink: String, dateOfEditing: String) {
        val text = content.trim()
        val link = videoLink.trim()
        val editing = dateOfEditing.trim()
        if (edited.value?.content == text && edited.value?.video == link) {
            return
        }
        edited.value = edited.value?.copy(content = text, video = link, edited = editing)
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
            thisPost.copy(
                id = id,
                content = edited.value?.content.toString(),
                video = edited.value?.video.toString(),
                edited = edited.value?.edited.toString()
            )
        repository.addPost(editedPost)
    }
}