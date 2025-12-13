package com.example.tasko.data.retrofit.models

data class User(
    val Id: Int,
    val FirstName: String,
    val LastName: String,
    val Username: String,
    val EmailAddress: String,
    val HashedPassword:String
)