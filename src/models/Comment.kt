package com.whub.models

import java.io.Serializable

data class Comment(
    val commentId: String,
    val hubId: String,
    val userId: String,
    val likeCount: String,
    val commentText: String,
    val datePosted: String
) : Serializable