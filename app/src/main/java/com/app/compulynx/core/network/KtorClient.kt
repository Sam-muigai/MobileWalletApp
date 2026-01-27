package com.app.compulynx.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val JSON = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    coerceInputValues = true
    prettyPrint = true
    isLenient = true
}

fun getKtorClient(): HttpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(
            json = JSON
        )
    }

    install(Logging) {
        level = LogLevel.BODY
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, "application/json")
    }
}