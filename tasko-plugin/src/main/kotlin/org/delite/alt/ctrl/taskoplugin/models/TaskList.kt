package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class TaskList (
    val id: Int,
    val id_project: Int,
    val name: String,
    val position: Int,
    val time_created: LocalDateTime,
    val id_user_created: Int
)