package com.peshale.nmedia.vmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.repository.PostRepository
import com.peshale.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
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

    fun editContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun cancelEdit() {
        edited.value = edited.value
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun toShareById(id: Long) = repository.toShareById(id)
    fun toViewById(id: Long) = repository.toViewById(id)
    fun removeById(id: Long) = repository.deleteById(id)
}