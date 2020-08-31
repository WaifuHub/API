package com.whub

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
        get("/demo") {
            call.respondText("HELLO WORLD!")
        }
    }
}
