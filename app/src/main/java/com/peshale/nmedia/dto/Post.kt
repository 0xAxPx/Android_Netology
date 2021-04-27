package com.peshale.nmedia.dto

import com.google.gson.annotations.SerializedName

data class Post(
    val id: Long,
    val author: String?,
    val authorAvatar: String,
    val content: String,
    val published: String,
    @SerializedName("likedByMe")
    val likedByMe: Boolean = false,
    @SerializedName("likes")
    var likes: Int = 0,
)