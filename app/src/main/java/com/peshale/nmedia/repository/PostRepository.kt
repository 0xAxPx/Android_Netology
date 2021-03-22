package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import com.peshale.nmedia.post.Post

interface PostRepository {

    fun get(): LiveData<Post>
    fun like()
}