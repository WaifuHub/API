package com.whub.di

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.whub.dao.EntryDao
import org.koin.dsl.module

val dynamoModule = module {
    single { EntryDao(get()) }
    single<DynamoDB> {
        DynamoDB(
            AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration(
                        System.getenv("DYNAMO_END_POINT"),
                        System.getenv("DYNAMO_REGION")
                    )
                )
                .build()
        )
    }
}