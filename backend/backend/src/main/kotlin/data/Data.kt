package com.example.data

import kotlinx.serialization.Serializable

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

