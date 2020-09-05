package com.whub.routes

import com.whub.Hubs
import com.whub.dao.DaoFacade
import com.whub.models.Hub
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Route.hubs(dao: DaoFacade) {
    post<Hubs> {
        val post = call.receive<Parameters>()
        val hubId = post["hubId"]
        val hubName = post["hubName"]
        var hubDescription = post["hubDescription"]
        var hubImageUrl = post["hubImageUrl"]
        if (hubDescription == null || hubImageUrl == null) {
            hubDescription = ""
            hubImageUrl = ""
        }
        if (hubId == null || hubName == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            val newHub = Hub(hubId, hubName, hubDescription, hubImageUrl)
            dao.createHub(newHub)
            call.respond(HttpStatusCode.OK, mapOf("CREATED" to "OK"))
        }
    }

    get<Hubs> {
        val post = call.receive<Parameters>()
        val hubId = post["hubId"]
        if (hubId == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            val hub = dao.getHubById(hubId)
            call.respond(HttpStatusCode.OK, mapOf("Hub" to hub))
        }
    }

    delete<Hubs> {
        val post = call.receive<Parameters>()
        val hubId = post["hubId"]
        if (hubId == null) {
            call.respond(HttpStatusCode.BadRequest, "BAD")
        } else {
            dao.deleteHub(hubId)
            call.respond(HttpStatusCode.OK, mapOf("CREATED" to "OK"))
        }
    }
}
