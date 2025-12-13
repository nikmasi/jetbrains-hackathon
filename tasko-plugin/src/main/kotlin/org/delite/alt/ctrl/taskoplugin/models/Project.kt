package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class Project (
    val id: Int,
    val name: String,
    val idOwner: Int,
    val timeCreated: LocalDateTime,
    val timeLastChange: LocalDateTime
)