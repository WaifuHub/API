package com.whub.dao

import com.whub.models.Comment
import com.whub.models.Hub
import com.whub.models.User
import java.io.Closeable

interface DaoFacade : Closeable {
    /**
     * initialize any required data e.g tables
     */
    fun init()

    /**
     * creates [user] in database
     */
    fun createUser(user: User)

    /**
     * retrieve user given [email], if not found returns null
     */
    fun getUserByEmail(email: String): User?

    /**
     * retrieve user given [uid], if not found returns null
     */
    fun getUserByUid(uid: String): User?

    /**
     * delete user from database given [uid]
     */
    fun deleteUser(uid: String)

    /**
     * create [comment] in database
     */
    fun createComment(comment: Comment)

    /**
     * retrieve comment given [commentId], if not found returns null
     */
    fun getCommentByCommentId(commentId: String): Comment?

    /**
     * returns all comments of a given hub given the [hubId], if not found
     * returns null
     */
    fun getAllCommentsByHubId(hubId: String): List<Comment>?

    /**
     * returns all comments of a given user given the [uid], if not found returns
     * null
     */
    fun getAllCommentsByUserId(uid: String): List<Comment>?

    /**
     * delete comment from database given it's [commentId]
     */
    fun deleteComments(commentId: String)

    /**
     * create [hub] in database
     */
    fun createHub(hub: Hub)

    /**
     * retrieve hub given [id], if not found returns null
     */
    fun getHubById(id: String): Hub?

    /**
     * delete hub from database given it's [id]
     */
    fun deleteHub(id: String)
}