package com.example.data

import kotlinx.serialization.Serializable

@Serializable
data class OpenAiRequest(val model: String = "gpt-3.5-turbo", val messages: List<Message>)
@Serializable
data class Message(val role: String, val content: String)

@Serializable
data class OpenAiResponse(
    val choices: List<Choice>? = null,
    val error: OpenAiError? = null
) {
    @Serializable
    data class Choice(val message: Message)
    @Serializable
    data class OpenAiError(val message: String, val type: String)
}
