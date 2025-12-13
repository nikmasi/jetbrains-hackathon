package org.delite.alt.ctrl.taskoplugin.services

import org.delite.alt.ctrl.taskoplugin.models.Task
import java.time.LocalDateTime

object TaskService {
    private val tasks = mutableListOf(
        Task(
            id = 1,
            idTaskList = 1,
            title = "Buy groceries",
            body = "Milk, eggs, bread",
            position = 0,
            timeCreated = LocalDateTime.now().minusDays(1),
            timeChanged = LocalDateTime.now().minusDays(1),
            checked = false
        ),
        Task(
            id = 2,
            idTaskList = 1,
            title = "Clean kitchen",
            body = "Wash dishes and wipe counters",
            position = 1,
            timeCreated = LocalDateTime.now().minusHours(5),
            timeChanged = LocalDateTime.now().minusHours(2),
            checked = true
        ),
        Task(
            id = 3,
            idTaskList = 2,
            title = "Finish report",
            body = "Complete the quarterly report",
            position = 0,
            timeCreated = LocalDateTime.now().minusDays(2),
            timeChanged = LocalDateTime.now().minusDays(1),
            checked = false
        )
    )

    fun getTasks(idTaskList: Int): List<Task> {
        return tasks
            .filter { it.idTaskList == idTaskList }
            .sortedBy { it.position }
    }
}