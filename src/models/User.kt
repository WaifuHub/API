package com.whub.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.io.Serializable

@DynamoDBTable(tableName = "UserTable")
data class User(
    @DynamoDBHashKey(attributeName = "uid")
    val userId: String,
    @DynamoDBAttribute(attributeName = "email")
    val email: String,
    @DynamoDBAttribute(attributeName = "displayName")
    val displayName: String,
    @DynamoDBAttribute(attributeName = "passwordHash")
    val passwordHash: String,
    @DynamoDBAttribute(attributeName = "profileImageUrl")
    val profileImageUrl: String
) : Serializable