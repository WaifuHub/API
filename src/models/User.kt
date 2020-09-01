package com.whub.models

import java.io.Serializable

data class User(
    val userId: String,
    val email: String,
    val displayName: String,
    val passwordHash: String,
    val profileImageUri: String
) : Serializable