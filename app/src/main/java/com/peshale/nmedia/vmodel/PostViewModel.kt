package com.peshale.nmedia.vmodel

import androidx.lifecycle.ViewModel
import com.peshale.nmedia.dto.Icons
import com.peshale.nmedia.repository.PostRepository
import com.peshale.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
    fun view() = repository.view()

    fun counter(icon: Icons) = data.value?.let {
        when (icon) {
            Icons.LIKES -> repository.counter(it.likes)
            Icons.SHARES -> repository.counter(it.share)
            Icons.VIEWS -> repository.counter(it.views)
        }
    }
}