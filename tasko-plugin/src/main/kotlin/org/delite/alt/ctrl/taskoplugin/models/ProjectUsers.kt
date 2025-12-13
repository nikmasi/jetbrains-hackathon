package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class ProjectUsers (
    val idProject: Int,
    val idUser : Int,
    val timeJoined: LocalDateTime
)