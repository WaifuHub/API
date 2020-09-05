package com.whub.routes

import com.whub.Users
import com.whub.dao.DaoFacade
import com.whub.models.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.users(dao: DaoFacade) {
    post<Users> {
        val post = call.receive<Parameters>()
        val userId = post["userId"]
        val email = post["email"]
        val displayName = post["displayName"]
        val passwordHash = post["passwordHash"]
        var profileImageUrl = post["profileImageUrl"]
        if (profileImageUrl == null) {
            profileImageUrl = ""
        }
        if (userId == null || email == null || displayName == null || passwordHash == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            val newUser = User(userId, email, displayName, passwordHash, profileImageUrl)
            dao.createUser(newUser)
            call.respond(HttpStatusCode.OK, mapOf("CREATED" to "OK"))
        }
    }

    get<Users> {
        val post = call.receive<Parameters>()
        val userId = post["userId"]
        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            val user = dao.getUserByUid(userId)
            call.respond(HttpStatusCode.OK, mapOf("User" to user))
        }
    }

    delete<Users> {
        val post = call.receive<Parameters>()
        val userId = post["userId"]
        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            dao.deleteUser(userId)
            call.respond(HttpStatusCode.OK, mapOf("CREATED" to "OK"))
        }
    }
}
