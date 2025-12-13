package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class Task(
    val id: Int = 0,
    val idTaskList: List<Int>,
    val title: String,
    val body: String,
    val position: Int,
    val timeCreated: LocalDateTime,
    var timeChanged: LocalDateTime,
    var checked: Boolean
)