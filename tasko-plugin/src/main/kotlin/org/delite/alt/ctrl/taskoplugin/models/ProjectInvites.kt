package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class ProjectInvites (
    val id_project: Int,
    val id_user: Int,
    val time_sent: LocalDateTime,
)