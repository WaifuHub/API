package com.whub.models

import java.io.Serializable
import java.util.*

data class Comment(
    val commentId: String,
    val hubId: String,
    val userId: String,
    val likeCount: Int,
    val commentText: String,
    val datePosted: Date
) : Serializable