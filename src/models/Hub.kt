package com.whub.models

import java.io.Serializable

data class Hub(
    val hubId: String,
    val hubName: String,
    val hubDescription: String,
    val hubImageUrl: String
) : Serializable