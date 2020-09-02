package com.whub.dao

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.whub.models.Comment
import com.whub.models.Hub
import com.whub.models.User


class EntryDao(private val dynamoDB: DynamoDB) : DaoFacade {
    companion object {
        private val TABLE_NAME_USERS = "UserTable"
        private val TABLE_NAME_COMMENTS = "CommentsTable"
        private val TABLE_NAME_HUBS = "HubsTable"
    }
    private val userTable = dynamoDB.getTable(TABLE_NAME_USERS)

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun createUser(user: User) {
        val userItem = Item()
            .withPrimaryKey("uid", user.userId)
            .withString("email", user.email)
            .withString("displayName", user.displayName)
            .withString("passwordHash", user.passwordHash)
            .withString("profileImageUrl", user.profileImageUrl)
        userTable.putItem(userItem)
    }

    override fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    override fun getUserByUid(uid: String): User? {
        TODO("Not yet implemented")
    }

    override fun deleteUser(uid: String) {
        TODO("Not yet implemented")
    }

    override fun createComment(comment: Comment) {
        TODO("Not yet implemented")
    }

    override fun getCommentByCommentId(commentId: String): Comment? {
        TODO("Not yet implemented")
    }

    override fun getAllCommentsByHubId(hubId: String): List<Comment>? {
        TODO("Not yet implemented")
    }

    override fun getAllCommentsByUserId(uid: String): List<Comment>? {
        TODO("Not yet implemented")
    }

    override fun deleteComments(commentId: String) {
        TODO("Not yet implemented")
    }

    override fun createHub(hub: Hub) {
        TODO("Not yet implemented")
    }

    override fun getHubById(id: String): Hub? {
        TODO("Not yet implemented")
    }

    override fun deleteHub(id: String) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}