package com.whub.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.io.Serializable

@DynamoDBTable(tableName = "CommentsTable")
data class Comment(
    @DynamoDBHashKey(attributeName = "commentId")
    val commentId: String,
    @DynamoDBAttribute(attributeName = "hubId")
    val hubId: String,
    @DynamoDBAttribute(attributeName = "userId")
    val userId: String,
    @DynamoDBAttribute(attributeName = "likeCount")
    val likeCount: String,
    @DynamoDBAttribute(attributeName = "commentText")
    val commentText: String,
    @DynamoDBAttribute(attributeName = "datePosted")
    val datePosted: String
) : Serializable