package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class Project (
    val id: Int,
    val name: String,
    val id_owner: Int,
    val time_created: LocalDateTime,
    val time_last_change: LocalDateTime
)