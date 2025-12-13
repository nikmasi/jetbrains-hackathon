package com.example.data

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

//import java.time.Instant

@Serializable
data class User(
    val first_name:String,
    val last_name:String,
    val username:String,
    val email_address:String,
    val hashed_password:String
)

@Serializable
data class UserRequest(
    val username:String,
    val hashed_password:String
)


@Serializable
data class MessageResponse(
    val message: String
)

@Serializable
data class Project @OptIn(ExperimentalTime::class) constructor(
    val id:Int,
    val name:String,
    val id_owner:Int,
    val time_created: Instant,
    val time_last_change:Instant
)

@Serializable
data class ProjectUsers(
    val id_project:Int,
    val id_user:Int,
    //val time_joined:Instant
)

@Serializable
data class ProjectInvites(
    val id_project:Int,
    val id_user:Int,
    //val time_sent:Instant
)

@Serializable
data class TaskLists(
    val id:Int,
    val id_project:Int,
    val name:String,
    val position:Int,
    val id_user_created:Int
    //val time_created:Instant
)

@Serializable
data class Task(
    val id:Int,
    val id_task_list:Int,
    val title:String,
    val body_text:String,
    val position:Int,
    val checked:Int,
    val id_user_created:Int
    //val time_created:Instant
    //val time_changed:Instant
)