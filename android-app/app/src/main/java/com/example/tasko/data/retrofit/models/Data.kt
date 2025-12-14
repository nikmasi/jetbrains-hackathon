package com.example.tasko.data.retrofit.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.ExperimentalTime

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
data class Project (
    val id:Int,
    val name:String,
    val id_owner:Int,
    //@Serializable(with = InstantSerializer::class)
    //val time_created: Instant,
    //@Serializable(with = InstantSerializer::class)
    //val time_last_change: Instant
)

object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString()) // ISO format
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}

@Serializable
data class ProjectList(
    val projects: List<Project>?
)

@Serializable
data class NewProject(
    val project: String,
    val username: String
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
data class TasksListList(
    val taskst: List<TaskLists>?
)
