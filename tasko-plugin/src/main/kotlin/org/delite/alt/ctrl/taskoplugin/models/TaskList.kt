package org.delite.alt.ctrl.taskoplugin.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskList (
    val id: Int,
    val id_project: Int,
    val name: String,
    val position: Int,
    val id_user_created: Int
)