package com.peshale.nmedia.vmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.repository.PostRepository
import com.peshale.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    avatar = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    likes = 0,
    shares = 0,
    views = 0,
    video = ""
)


class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun addPost() {
        edited.value?.let {
            repository.addPost(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String, videoLink: String) {
        val text = content.trim()
        val link = videoLink.trim()
        if (edited.value?.content == text && edited.value?.video == link) {
            return
        }
        edited.value = edited.value?.copy(content = text, video = link)
    }

    fun cancelEdit() {
        edited.value = edited.value
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun toShareById(id: Long) = repository.toShareById(id)
    fun toViewById(id: Long) = repository.toViewById(id)
    fun deleteById(id: Long) = repository.deleteById(id)
}