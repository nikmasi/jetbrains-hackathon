package org.delite.alt.ctrl.taskoplugin.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject

object Http {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    fun post(url: String, json: JsonObject): JsonObject = runBlocking {
        client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(json)
        }.body()
    }

    fun get(url: String): JsonObject = runBlocking {
        client.get(url).body()
    }
}