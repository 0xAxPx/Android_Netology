package com.peshale.nmedia.vmodel

import androidx.lifecycle.ViewModel
import com.peshale.nmedia.repository.PostRepository
import com.peshale.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
    fun view() = repository.view()
    fun counterLikes() = data.value?.let { repository.counter(it.likes) }
    fun counterShares() = data.value?.let { repository.counter(it.share) }
    fun counterViews() = data.value?.let { repository.counter(it.views) }
}