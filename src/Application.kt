package com.whub

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.whub.dao.DaoFacade
import com.whub.dao.EntryDao
import com.whub.dao.EntryDaoCache
import com.whub.di.dynamoModule
import com.whub.routes.comments
import com.whub.routes.hubs
import com.whub.routes.userByEmail
import com.whub.routes.users
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger
import java.io.File

/*
 * Classes used for the locations feature to build urls and register routes.
 */
@KtorExperimentalLocationsAPI
@Location("/hubs")
class Hubs

@KtorExperimentalLocationsAPI
@Location("/comments")
class Comments

@KtorExperimentalLocationsAPI
@Location("/users")
class Users

@KtorExperimentalLocationsAPI
@Location("/users/{email}")
class UserByEmail(val email: String)

/**
 * Constructs a facade with the database
 */
val db: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
    AwsClientBuilder.EndpointConfiguration(
        System.getenv("DYNAMO_END_POINT"),
        System.getenv("DYNAMO_REGION")
    )
).build()

val dao: DaoFacade =
    EntryDaoCache(EntryDao(db), File("ehcache/hubs"), File("ehcache/comments"))

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    dao.init()
    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(Koin) {
        slf4jLogger()
        modules(dynamoModule)
    }
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter(" ", "\n"))
            })

        }
    }

    routing {
        hubs(dao)
        comments(dao)
        users(dao)
        userByEmail(dao)
    }
}
