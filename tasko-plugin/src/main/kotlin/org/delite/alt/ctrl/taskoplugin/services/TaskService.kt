package org.delite.alt.ctrl.taskoplugin.services

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.put
import org.delite.alt.ctrl.taskoplugin.models.Task
import java.time.LocalDateTime

object TaskService {
    val apiURL: String = "http://localhost:8080"

    fun getTasks(idTaskList: Int): List<Task> {
        val response = Http.post(
            "${apiURL}/selectAllTasksOfTaskLists",
            buildJsonObject {
                put("name", idTaskList)
            }
        )
        println(idTaskList.toString() + " " + response)
        return Json.decodeFromJsonElement<List<Task>>(response["tasks"] ?: return emptyList())
    }

    fun addTask(idTaskList: Int, taskTitle: String, bodyText: String) {
        val response = Http.post(
            "${apiURL}/createTask",
            buildJsonObject {
                put("id", 0)
                put("id_task_list", idTaskList)
                put("title", taskTitle)
                put("body_text", bodyText)
                put("position", 0)
                put("checked", 0)
                put("id_user_created", 0)
            }
        )
    }
}