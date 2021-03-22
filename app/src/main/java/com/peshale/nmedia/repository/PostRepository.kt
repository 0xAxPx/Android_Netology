package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import com.peshale.nmedia.dto.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun toShareById(id: Long)
    fun toViewById(id: Long)
}