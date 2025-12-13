package org.delite.alt.ctrl.taskoplugin.models

import java.time.LocalDateTime

data class User(
    /* TODO: Do authentication on the backend, thus remove password field */
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val emailAddress: String,
    val password: String
)