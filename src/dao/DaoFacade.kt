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
    fun createUser(user: User): Int

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
    fun deleteUser(uid: String): Int

    /**
     * create [comment] in database
     */
    fun createComment(comment: Comment): Int

    /**
     * retrieve comment given [commentId], if not found returns null
     */
    fun getCommentByCommentId(commentId: String): Comment?

    /**
     * delete comment from database given it's [commentId]
     */
    fun deleteComment(commentId: String): Int

    /**
     * create [hub] in database
     */
    fun createHub(hub: Hub): Int

    /**
     * retrieve hub given [id], if not found returns null
     */
    fun getHubById(id: String): Hub?

    /**
     * delete hub from database given it's [id]
     */
    fun deleteHub(id: String): Int
}