package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class ProjectInvites (
    /* TODO: Do authentication on the backend, thus remove password field... */
    val idProject: Int,
    val idUser: Int,
    val timeSent: LocalDateTime,

)