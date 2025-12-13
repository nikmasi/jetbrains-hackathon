package com.example.repository

import com.example.data.User

interface RepoInterface {
    fun createUser(user: User): Boolean

    fun selectUser(username:String, password:String): User?
}