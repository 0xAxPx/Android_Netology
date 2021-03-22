package com.peshale.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    var likes: Int = 0,
    val share: Int = 0,
    val views: Int = 0
)