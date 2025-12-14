package org.delite.alt.ctrl.taskoplugin.services

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

class ProjectService {
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

    fun projectExists(projectName: String, username: String, password: String): Boolean {
        val response = Http.post(
            "${apiURL}/createProject",
            buildJsonObject {
                put("username", projectName)
                put("username", username)
            }
        )
    }
}