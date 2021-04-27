package com.peshale.nmedia.repository

import androidx.lifecycle.LiveData
import com.peshale.nmedia.dto.Post

interface PostRepository {

    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun unlikeById(id: Long): Post
    fun deleteById(id: Long)
    fun addPost(post: Post): Post
    fun findPostById(id: Long): Post
    fun updatePost(id: Post): Post

    fun getAllAsync(callback: GetAllCallback)
    fun getPostAsync(id: Long, callback: GetPostCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface GetPostCallback {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }
}