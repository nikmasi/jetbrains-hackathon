package org.delite.alt.ctrl.taskoplugin.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Task(
    val id: Int = 0,
    val id_task_list: Int,
    val title: String,
    val body_text : String,
    val position: Int,
    val checked: Int,
    val id_user_created: Int
)