package org.delite.alt.ctrl.taskoplugin.services

import org.delite.alt.ctrl.taskoplugin.models.Task
import java.time.LocalDateTime

object TaskService {
    private val tasks = mutableListOf(
        Task(
            id = 1,
            id_task_list = 1,
            title = "Set up project",
            body_text = "Create a new Kotlin project and configure Gradle dependencies.",
            position = 0,
            checked = true,
            time_created = LocalDateTime.now().minusDays(3),
            time_changed = LocalDateTime.now().minusDays(3)
        ),
        Task(
            id = 2,
            id_task_list = 1,
            title = "Design data models",
            body_text = "Define data classes for Task, TaskList, and User.",
            position = 1,
            checked = true,
            time_created = LocalDateTime.now().minusDays(2),
            time_changed = LocalDateTime.now().minusDays(1),
        ),
        Task(
            id = 3,
            id_task_list = 1,
            title = "Implement repository layer",
            body_text = "Create repository interfaces and mock implementations.",
            position = 2,
            checked = false,
            time_created = LocalDateTime.now().minusDays(2),
            time_changed = LocalDateTime.now().minusDays(2),
        ),
        Task(
            id = 4,
            id_task_list = 2,
            title = "Build UI screen",
            body_text = "Create the task list screen using Jetpack Compose.",
            position = 0,
            checked = false,
            time_created = LocalDateTime.now().minusDays(1),
            time_changed = LocalDateTime.now().minusDays(1)
        ),
        Task(
            id = 5,
            id_task_list = 2,
            title = "Add click handling",
            body_text = "Handle task check/uncheck and item clicks.",
            position = 1,
            checked = false,
            time_created = LocalDateTime.now().minusHours(20),
            time_changed = LocalDateTime.now().minusHours(5),
        ),
        Task(
            id = 6,
            id_task_list = 3,
            title = "Write unit tests",
            body_text = "Add unit tests for TaskService and repositories.",
            position = 0,
            checked = false,
            time_created = LocalDateTime.now().minusHours(10),
            time_changed = LocalDateTime.now().minusHours(10),
        )
    )

    fun getTasks(idTaskList: Int): List<Task> {
        return tasks.filter { it.id_task_list == idTaskList }.sortedBy { it.position }
    }
}