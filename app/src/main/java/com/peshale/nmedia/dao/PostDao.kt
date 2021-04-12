package com.peshale.nmedia.dao

import com.peshale.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likedById(id: Long)
    fun toShareById(id: Long)
    fun toViewById(id: Long)
    fun addPost(post: Post) : Post
    fun deleteById(id: Long)
    fun findPostById(id: Long): Post
}