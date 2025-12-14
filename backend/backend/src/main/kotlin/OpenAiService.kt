package com.example

import com.example.data.Message
import com.example.data.OpenAiRequest
import com.example.data.OpenAiResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.WebSocketDeflateExtension.Companion.install
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class OpenAiService(private val apiKey: String) {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    suspend fun sendMessage(prompt: String): String {
        val requestBody = OpenAiRequest(
            messages = listOf(Message(role = "user", content = prompt))
        )

        val response: OpenAiResponse = client.post("https://api.openai.com/v1/chat/completions") {
            header("Authorization", "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

        return response.choices?.firstOrNull()?.message?.content
            ?: response.error?.message
            ?: "No response"
    }

}