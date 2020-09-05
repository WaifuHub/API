package com.whub.routes


import com.whub.Comments
import com.whub.dao.DaoFacade
import com.whub.models.Comment
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.comments(dao: DaoFacade) {
    post<Comments> {
        val post = call.receive<Parameters>()
        val commentId = post["commentId"]
        val hubId = post["hubId"]
        val userId = post["userId"]
        val datePosted = post["datePosted"]
        var likeCount = post["likeCount"]
        var commentText = post["commentText"]
        if (likeCount == null || commentText == null) {
            likeCount = ""
            commentText = ""
        }
        if (commentId == null || hubId == null || userId == null || datePosted == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            val newComment = Comment(commentId, hubId, userId, likeCount, commentText, datePosted)
            dao.createComment(newComment)
            call.respond(HttpStatusCode.OK, mapOf("CREATED" to "OK"))
        }
    }

    get<Comments> {
        val post = call.receive<Parameters>()
        val commentId = post["commentId"]
        if (commentId == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            val comment = dao.getCommentByCommentId(commentId)
            call.respond(HttpStatusCode.OK, mapOf("Comment" to comment))
        }
    }

    delete<Comments> {
        val post = call.receive<Parameters>()
        val commentId = post["commentId"]
        if (commentId == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            dao.deleteComment(commentId)
            call.respond(HttpStatusCode.OK, mapOf("CREATED" to "OK"))
        }
    }
}
