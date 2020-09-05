package com.whub.models

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.io.Serializable

@DynamoDBTable(tableName = "HubTable")
data class Hub(
    @DynamoDBHashKey(attributeName = "hubId")
    val hubId: String,
    @DynamoDBAttribute(attributeName = "hubName")
    val hubName: String,
    @DynamoDBAttribute(attributeName = "hubDescription")
    val hubDescription: String,
    @DynamoDBAttribute(attributeName = "hubImageUrl")
    val hubImageUrl: String
) : Serializable