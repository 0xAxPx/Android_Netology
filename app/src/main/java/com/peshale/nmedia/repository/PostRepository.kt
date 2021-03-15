package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import com.peshale.nmedia.dto.Post

interface PostRepository {

    fun get(): LiveData<Post>
    fun like()
    fun share()
    fun view()
    fun counter(count: Int): String
}