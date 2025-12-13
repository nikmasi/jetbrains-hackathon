package com.example.repository

import com.example.closeResources
import com.example.data.Project
import com.example.data.ProjectInvites
import com.example.data.ProjectUsers
import com.example.data.Task
import com.example.data.TaskLists
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

    override fun createProject(project: Project): Boolean {
        val query = """
            INSERT INTO projects (name, id_owner)
            VALUES (?, ?)
        """

        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            if (statement == null) {
                println("Prepare statement failed: statement is null")
                return false
            }

            statement.setString(1, project.name)
            statement.setInt(2, project.id_owner)

            statement.executeUpdate()

            resultSet = statement.generatedKeys
            if (resultSet?.next() == true) {
                // Generated ID present
                return true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }
        return false
    }

    override fun createProjectUsers(projectUsers: ProjectUsers): Boolean {
        val query = """
            INSERT INTO project_users (id_project, id_user)
            VALUES (?, ?)
        """

        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            if (statement == null) {
                println("Prepare statement failed: statement is null")
                return false
            }

            statement.setInt(1, projectUsers.id_project)
            statement.setInt(2, projectUsers.id_user)

            statement.executeUpdate()

            resultSet = statement.generatedKeys
            if (resultSet?.next() == true) {
                return true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }
        return false
    }

    override fun createProjectInvites(projectInvites: ProjectInvites): Boolean {
        val query = """
            INSERT INTO project_invites (id_project, id_user)
            VALUES (?, ?)
        """

        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            if (statement == null) {
                println("Prepare statement failed: statement is null")
                return false
            }

            statement.setInt(1, projectInvites.id_project)
            statement.setInt(2, projectInvites.id_user)

            statement.executeUpdate()

            resultSet = statement.generatedKeys
            if (resultSet?.next() == true) {
                return true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }
        return false
    }

    override fun createTaskList(taskLists: TaskLists): Boolean {
        val query = """
            INSERT INTO task_lists (id_project, name, position, id_user_created)
            VALUES (?, ?, ?, ?)
        """

        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            if (statement == null) {
                println("Prepare statement failed: statement is null")
                return false
            }

            statement.setInt(1, taskLists.id_project)
            statement.setString(2, taskLists.name)
            statement.setInt(3, taskLists.position)
            statement.setInt(4, taskLists.id_user_created)

            statement.executeUpdate()

            resultSet = statement.generatedKeys
            if (resultSet?.next() == true) {
                return true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }
        return false
    }

    override fun createTask(task: Task): Boolean {
        val query = """
            INSERT INTO tasks (id_task_list, title, body_text, position, checked, id_user_created)
            VALUES (?, ?, ?, ?, ?, ?)
        """

        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            if (statement == null) {
                println("Prepare statement failed: statement is null")
                return false
            }

            statement.setInt(1, task.id_task_list)
            statement.setString(2, task.title)
            statement.setString(3, task.body_text)
            statement.setInt(4, task.position)
            statement.setInt(5, task.checked)
            statement.setInt(6, task.id_user_created)

            statement.executeUpdate()

            resultSet = statement.generatedKeys
            if (resultSet?.next() == true) {
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