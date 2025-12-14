package org.delite.alt.ctrl.taskoplugin.services

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.delite.alt.ctrl.taskoplugin.models.TaskList
import org.delite.alt.ctrl.taskoplugin.services.UserService.apiURL
import java.time.LocalDateTime

object TaskListService {
    val apiURL: String = "http://localhost:8080"

    fun getTaskListsByProject(username: String, projectName: String): List<TaskList> {
        val response = Http.post(
            "${apiURL}/selectAllTasksProject",
            buildJsonObject {
                put("username", username)
                put("project", projectName)
            }
        )

        return Json.decodeFromJsonElement<List<TaskList>>(response["taskst"] ?: return emptyList())
    }

    fun addTaskList(idProject: Int, name: String) {
        /* TODO: Implement this after implementation on backend is complete. */
    }
}