package com.example

import java.sql.Connection
import java.sql.DriverManager

class DatabaseService(private val dbUrl: String, private val user: String, private val password: String) {

    fun getDatabaseConnection(): Connection {
        return DriverManager.getConnection(
            dbUrl, user, password
        )
    }
}