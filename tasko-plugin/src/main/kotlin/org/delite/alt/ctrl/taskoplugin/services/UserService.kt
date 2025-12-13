package org.delite.alt.ctrl.taskoplugin.services

import org.delite.alt.ctrl.taskoplugin.models.User

object UserService {
    /* TODO: Remove mock data, and also perform login/registration on the backend. */
    private val users = mutableListOf(
        User(1, "John", "Doe", "jdoe", "jdoe@example.com", "test123"),
        User(2, "Alice", "Smith", "asmith", "asmith@example.com", "test123"),
        User(3, "Bruce", "Wayne", "bwayne", "bruce@wayneenterprises.com", "test123")
    )

    fun login(username: String, password: String): User? {
        return users.find { it.username == username && it.hashed_password == password }
    }

    fun register(firstName: String, lastName: String, username: String, emailAddress: String, password: String): User {
        val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
        val newUser = User(newId, firstName, lastName, username, emailAddress, password)
        users.add(newUser)
        return newUser
    }
}