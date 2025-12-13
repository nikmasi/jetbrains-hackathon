package com.example.repository

import com.example.closeResources
import com.example.data.User
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class Repository(private val connection: Connection): RepoInterface {
    override fun createUser(user: User): Boolean {
        val query = """
            INSERT INTO users (first_name, last_name, username, email_address, hashed_password)
            VALUES (?, ?, ?, ?, ?)
        """

        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            if (statement == null) {
                println("Prepare statement failed: statement is null")
                return false
            }

            statement.setString(1,user.first_name)
            statement.setString(2, user.last_name)
            statement.setString(3, user.username)
            statement.setString(4, user.email_address)
            statement.setString(5, user.hashed_password)
            statement.executeUpdate()

            resultSet = statement.generatedKeys
            if (resultSet?.next() == true) {
                // Vraca generisani ID
                return true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }
        return false
    }
    override fun selectUser(username: String, password: String): User? {
        val query = """
        SELECT first_name, last_name, username, email_address, hashed_password
        FROM users
        WHERE username = ? AND hashed_password = ?
    """

        connection.prepareStatement(query).use { statement ->
            statement.setString(1, username)
            statement.setString(2, password)

            statement.executeQuery().use { rs ->
                return if (rs.next()) {
                    User(
                        first_name = rs.getString("first_name"),
                        last_name = rs.getString("last_name"),
                        username = rs.getString("username"),
                        email_address = rs.getString("email_address"),
                        hashed_password = rs.getString("hashed_password")
                    )
                } else {
                    null
                }
            }
        }
    }

}