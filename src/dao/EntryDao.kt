package com.whub.dao

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.whub.models.Comment
import com.whub.models.Hub
import com.whub.models.User


class EntryDao(dynamoDBClient: AmazonDynamoDB) : DaoFacade {
    companion object {
        private val MAPPER: ObjectMapper = jacksonObjectMapper()
        private val TABLE_NAME_USERS = "UserTable"
        private val TABLE_NAME_COMMENTS = "CommentsTable"
        private val TABLE_NAME_HUBS = "HubsTable"
    }

    private val dynamoDb = DynamoDB(dynamoDBClient)
    private val userTable = dynamoDb.getTable(TABLE_NAME_USERS)
    private val commentsTable = dynamoDb.getTable(TABLE_NAME_COMMENTS)
    private val hubsTable = dynamoDb.getTable(TABLE_NAME_HUBS)

    override fun init() {}

    override fun createUser(user: User): Int {
        return try {
            val userItem = Item()
                .withPrimaryKey("userId", user.userId)
                .withString("email", user.email)
                .withString("displayName", user.displayName)
                .withString("passwordHash", user.passwordHash)
                .withString("profileImageUrl", user.profileImageUrl)
            userTable.putItem(userItem)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun getUserByEmail(email: String): User? {
        val userJson = userTable.getItem("email", email).toJSON()
        return if (userJson != null) {
            MAPPER.readValue(userJson)
        } else {
            null
        }
    }

    override fun getUserByUid(uid: String): User? {
        val userJson = userTable.getItem("userId", uid).toJSON()
        return if (userJson != null) {
            MAPPER.readValue(userJson)
        } else {
            null
        }
    }

    override fun deleteUser(uid: String): Int {
        return try {
            userTable.deleteItem("userId", uid)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun createComment(comment: Comment): Int {
        return try {
            val commentItem = Item()
                .withPrimaryKey("commentId", comment.commentId)
                .withString("hubId", comment.hubId)
                .withString("userId", comment.userId)
                .withString("likeCount", comment.likeCount)
                .withString("commentText", comment.commentText)
                .withString("datePosted", comment.datePosted)
            commentsTable.putItem(commentItem)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun getCommentByCommentId(commentId: String): Comment? {
        val commentJson = commentsTable.getItem("commentId", commentId).toJSON()
        return if (commentJson != null) {
            MAPPER.readValue(commentJson)
        } else {
            null
        }
    }

    override fun deleteComment(commentId: String): Int {
        return try {
            commentsTable.deleteItem("commentId", commentId)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun createHub(hub: Hub): Int {
        return try {
            val hubItem = Item()
                .withPrimaryKey("hubId", hub.hubId)
                .withString("hubName", hub.hubName)
                .withString("hubDescription", hub.hubDescription)
                .withString("hubImageUrl", hub.hubImageUrl)
            hubsTable.putItem(hubItem)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun getHubById(id: String): Hub? {
        val hubJson = commentsTable.getItem("hubId", id).toJSON()
        return if (hubJson != null) {
            MAPPER.readValue(hubJson)
        } else {
            null
        }
    }

    override fun deleteHub(id: String): Int {
        return try {
            hubsTable.deleteItem("hubId", id)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun close() {}
}