package org.delite.alt.ctrl.taskoplugin.services

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

object ProjectService {
    val apiURL: String = "http://localhost:8080"

    fun createProject(projectName: String, username: String) {
        val response = Http.post(
            "${apiURL}/createProject",
            buildJsonObject {
                put("project", projectName)
                put("username", username)
            }
        )
    }
}