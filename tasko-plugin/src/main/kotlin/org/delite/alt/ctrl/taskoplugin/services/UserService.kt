package org.delite.alt.ctrl.taskoplugin.services

import kotlinx.serialization.json.*

object UserService {
    val apiURL: String = "http://localhost:8080"

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
        val response = Http.post(
            "${apiURL}/signUp",
            buildJsonObject {
                put("first_name", firstName)
                put("last_name", lastName)
                put("username", username)
                put("email_address", emailAddress)
                put("hashed_password", password)
            }
        )

        val message: String = response["message"]?.jsonPrimitive?.content ?: "FALSE"
        return (message == "TRUE")
    }
}