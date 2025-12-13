package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class TaskList (
    val idProject: Int,
    val name: String,
    val timeCreated: LocalDateTime,
    val order: Int, /* mozda visak */
    val position: Int
)