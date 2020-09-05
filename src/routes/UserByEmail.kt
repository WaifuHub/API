package com.whub.routes

import com.whub.UserByEmail
import com.whub.dao.DaoFacade
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.userByEmail(dao: DaoFacade) {
    get<UserByEmail> { request ->
        val email = request.email
        val user = dao.getUserByEmail(email)
        call.respond(HttpStatusCode.OK, mapOf("User" to user))
    }
}