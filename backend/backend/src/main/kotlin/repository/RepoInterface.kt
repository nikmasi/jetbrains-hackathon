package com.example.repository

import com.example.data.*

interface RepoInterface {
    fun createUser(user: User): Boolean
    fun createProject(project: Project): Boolean
    fun createProjectUsers(projectUsers: ProjectUsers): Boolean
    fun createProjectInvites(projectInvites: ProjectInvites): Boolean
    fun createTaskList(taskLists: TaskLists): Boolean
    fun createTask(task: Task): Boolean


    fun selectUser(username:String, password:String): User?
    fun selectProjectsOfUser(user: User): List<Project>
    fun selectProjectUsersOfProject(project: Project): List<User>
    fun selectProjectInvitesOfProject(project: Project): List<User>
    fun selectAllTaskListsOfProject(project: Project): List<TaskLists>
    fun selectAllTasksOfTaskLists(taskLists: TaskLists): List<Task>

    fun updateProject(project: Project): Boolean // Changing project name
    fun updateTaskTitle(task: Task): Boolean // Changing tasks title
    fun updateTaskBody(task: Task): Boolean // Changing tasks body
    fun updateTaskPosition(task: Task): Boolean // Changing tasks position
    fun updateTaskListsName(taskLists: TaskLists): Boolean // Changing tasklist name
    fun updateTaskListsPosition(taskLists: TaskLists): Boolean // Changing tasklist position

    fun deleteProject(project: Project): Boolean
    fun deleteProjectUsers(projectUsers: ProjectUsers): Boolean
    fun deleteProjectInvites(projectInvites: ProjectInvites): Boolean
    fun deleteTaskList(taskLists: TaskLists): Boolean
    fun deleteTask(task: Task): Boolean
}