package com.whub

import com.whub.di.dynamoModule
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(dynamoModule)
    }

    routing {
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
        get("/demo") {
            call.respondText("HELLO WORLD!")
        }
    }
}
