package org.delite.alt.ctrl.taskoplugin.services

import org.delite.alt.ctrl.taskoplugin.models.TaskList
import java.time.LocalDateTime

object TaskListService {
    private val taskLists = mutableListOf(
        TaskList(
            id = 1,
            id_project = 1,
            name = "TODO",
            time_created = LocalDateTime.now().minusDays(4),
            position = 0
        ),
        TaskList(
            id = 2,
            id_project = 1,
            name = "IN PROCESS",
            time_created = LocalDateTime.now().minusDays(2),
            position = 1
        ),
        TaskList(
            id = 3,
            id_project = 1,
            name = "DONE",
            time_created = LocalDateTime.now().minusDays(1),
            position = 2
        )
    )

    /** Get all task lists for a project */
    fun getTaskListsByProject(idProject: Int): List<TaskList> {
        return taskLists.filter { it.id_project == idProject }.sortedBy { it.position }
    }

    fun addTaskList(idProject: Int, name: String) {
        val nextPosition = taskLists.filter { it.id_project == idProject }.maxOfOrNull { it.position }?.plus(1) ?: 0
        taskLists.addLast(TaskList(id=taskLists.size + 1, id_project = idProject, name=name, time_created = LocalDateTime.now(), position = nextPosition))
    }
}