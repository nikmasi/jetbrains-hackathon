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
}