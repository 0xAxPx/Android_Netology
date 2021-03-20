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
    published = "")


class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.addPost(it)
        }
        edited.value = empty
    }

    fun editContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if  (it.content == text) {
                repository
            }
            edited.value = it.copy(content = text)
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun toShareById(id: Long) = repository.toShareById(id)
    fun toViewById(id: Long) = repository.toViewById(id)
    fun removeById(id: Long) = repository.removeById(id)
}