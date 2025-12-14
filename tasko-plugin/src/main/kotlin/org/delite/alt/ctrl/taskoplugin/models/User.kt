package org.delite.alt.ctrl.taskoplugin.models

data class User(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val username: String,
    val email_address: String,
    val hashed_password: String /* TODO: Remove this? */
)