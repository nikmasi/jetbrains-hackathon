package org.delite.alt.ctrl.taskoplugin.models

data class User(
    /* TODO: Do authentication on the backend, thus remove password field... */
    val id: Int,
    val first_name: String,
    val last_name: String,
    val username: String,
    val email_address: String,
    val hashed_password: String
)