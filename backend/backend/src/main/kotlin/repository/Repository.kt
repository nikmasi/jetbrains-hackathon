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

    override fun selectProjectsOfUser(user: User): List<Project> {
        val query = """
            SELECT DISTINCT p.id, p.name, p.id_owner
            FROM projects p
            LEFT JOIN project_users pu ON p.id = pu.id_project
            LEFT JOIN users u ON (pu.id_user = u.id OR p.id_owner = u.id)
            WHERE u.username = ?
        """

        val results = mutableListOf<Project>()
        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setString(1, user.username)
            val rs = statement.executeQuery()
            while (rs.next()) {
                /*
                results.add(
                    Project(
                        id = rs.getInt("id"),
                        name = rs.getString("name"),
                        id_owner = rs.getInt("id_owner")
                    )
                )
                 */
            }
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return results
    }

    override fun selectProjectUsersOfProject(project: Project): List<User> {
        val query = """
            SELECT DISTINCT u.first_name, u.last_name, u.username, u.email_address, u.hashed_password
            FROM users u
            LEFT JOIN project_users pu ON u.id = pu.id_user
            WHERE pu.id_project = ? OR u.id = (SELECT id_owner FROM projects WHERE id = ?)
        """

        val results = mutableListOf<User>()
        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, project.id)
            statement.setInt(2, project.id)
            val rs = statement.executeQuery()
            while (rs.next()) {
                results.add(
                    User(
                        first_name = rs.getString("first_name"),
                        last_name = rs.getString("last_name"),
                        username = rs.getString("username"),
                        email_address = rs.getString("email_address"),
                        hashed_password = rs.getString("hashed_password")
                    )
                )
            }
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return results
    }

    override fun selectProjectInvitesOfProject(project: Project): List<User> {
        val query = """
            SELECT u.first_name, u.last_name, u.username, u.email_address, u.hashed_password
            FROM users u
            JOIN project_invites pi ON u.id = pi.id_user
            WHERE pi.id_project = ?
        """

        val results = mutableListOf<User>()
        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, project.id)
            val rs = statement.executeQuery()
            while (rs.next()) {
                results.add(
                    User(
                        first_name = rs.getString("first_name"),
                        last_name = rs.getString("last_name"),
                        username = rs.getString("username"),
                        email_address = rs.getString("email_address"),
                        hashed_password = rs.getString("hashed_password")
                    )
                )
            }
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return results
    }

    override fun selectAllTaskListsOfProject(project: Project): List<TaskLists> {
        val query = """
            SELECT id, id_project, name, position, id_user_created
            FROM task_lists
            WHERE id_project = ?
            ORDER BY position
        """

        val results = mutableListOf<TaskLists>()
        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, project.id)
            val rs = statement.executeQuery()
            while (rs.next()) {
                results.add(
                    TaskLists(
                        id = rs.getInt("id"),
                        id_project = rs.getInt("id_project"),
                        name = rs.getString("name"),
                        position = rs.getInt("position"),
                        id_user_created = rs.getInt("id_user_created")
                    )
                )
            }
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return results
    }

    override fun selectAllTasksOfTaskLists(taskLists: TaskLists): List<Task> {
        val query = """
            SELECT id, id_task_list, title, body_text, position, checked, id_user_created
            FROM tasks
            WHERE id_task_list = ?
            ORDER BY position
        """

        val results = mutableListOf<Task>()
        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, taskLists.id)
            val rs = statement.executeQuery()
            while (rs.next()) {
                results.add(
                    Task(
                        id = rs.getInt("id"),
                        id_task_list = rs.getInt("id_task_list"),
                        title = rs.getString("title"),
                        body_text = rs.getString("body_text"),
                        position = rs.getInt("position"),
                        checked = rs.getInt("checked"),
                        id_user_created = rs.getInt("id_user_created")
                    )
                )
            }
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return results
    }

    override fun updateProject(project: Project): Boolean {
        val query = """
            UPDATE projects SET name = ? WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setString(1, project.name)
            statement.setInt(2, project.id)
            val updated = statement.executeUpdate()
            return updated > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun updateTaskTitle(task: Task): Boolean {
        val query = """
            UPDATE tasks SET title = ? WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setString(1, task.title)
            statement.setInt(2, task.id)
            val updated = statement.executeUpdate()
            return updated > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun updateTaskBody(task: Task): Boolean {
        val query = """
            UPDATE tasks SET body_text = ? WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setString(1, task.body_text)
            statement.setInt(2, task.id)
            val updated = statement.executeUpdate()
            return updated > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun updateTaskPosition(task: Task): Boolean {
        val query = """
            UPDATE tasks SET position = ? WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, task.position)
            statement.setInt(2, task.id)
            val updated = statement.executeUpdate()
            return updated > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun updateTaskListsName(taskLists: TaskLists): Boolean {
        val query = """
            UPDATE task_lists SET name = ? WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setString(1, taskLists.name)
            statement.setInt(2, taskLists.id)
            val updated = statement.executeUpdate()
            return updated > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun updateTaskListsPosition(taskLists: TaskLists): Boolean {
        val query = """
            UPDATE task_lists SET position = ? WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, taskLists.position)
            statement.setInt(2, taskLists.id)
            val updated = statement.executeUpdate()
            return updated > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun deleteProject(project: Project): Boolean {
        val query = """
            DELETE FROM projects WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, project.id)
            val deleted = statement.executeUpdate()
            return deleted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun deleteProjectUsers(projectUsers: ProjectUsers): Boolean {
        val query = """
            DELETE FROM project_users WHERE id_project = ? AND id_user = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, projectUsers.id_project)
            statement.setInt(2, projectUsers.id_user)
            val deleted = statement.executeUpdate()
            return deleted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun deleteProjectInvites(projectInvites: ProjectInvites): Boolean {
        val query = """
            DELETE FROM project_invites WHERE id_project = ? AND id_user = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, projectInvites.id_project)
            statement.setInt(2, projectInvites.id_user)
            val deleted = statement.executeUpdate()
            return deleted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun deleteTaskList(taskLists: TaskLists): Boolean {
        val query = """
            DELETE FROM task_lists WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, taskLists.id)
            val deleted = statement.executeUpdate()
            return deleted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

    override fun deleteTask(task: Task): Boolean {
        val query = """
            DELETE FROM tasks WHERE id = ?
        """

        var statement: PreparedStatement? = null

        try {
            statement = connection.prepareStatement(query)
            statement.setInt(1, task.id)
            val deleted = statement.executeUpdate()
            return deleted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResources(connection, statement, null)
        }

        return false
    }

}