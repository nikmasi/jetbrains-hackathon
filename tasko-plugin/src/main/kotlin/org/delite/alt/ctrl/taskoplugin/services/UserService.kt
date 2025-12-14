package org.delite.alt.ctrl.taskoplugin.services

import org.delite.alt.ctrl.taskoplugin.models.User
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*


object UserService {
    val apiURL: String = "http://localhost:8080"

    object Http {

        private val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }

        // NON-SUSPEND, BLOCKING, SIMPLE
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

    fun login(username: String, password: String): Boolean {
        val response = Http.post(
            "${apiURL}/logIn",
            buildJsonObject {
                put("username", username)
                put("hashed_password", password)
            }
        )

        val message: String = response["message"]?.jsonPrimitive?.content ?: "FALSE"
        return (message == "TRUE")
    }

    fun register(firstName: String, lastName: String, username: String, emailAddress: String, password: String): Boolean {
        return false
    }
}