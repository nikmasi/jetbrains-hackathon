package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class Task(
    val id: Int = 0,
    val id_task_list: Int,
    val title: String,
    val body_text : String,
    val position: Int,
    val checked: Boolean,
    val time_created: LocalDateTime,
    val time_changed: LocalDateTime,
    val id_user_created: Int
)