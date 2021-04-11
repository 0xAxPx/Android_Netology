package com.peshale.nmedia.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val author: String?,
    val content: String,
    val video: String?,
    val published: String,
    val likedByMe: Boolean = false,
    var likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0
) : Parcelable