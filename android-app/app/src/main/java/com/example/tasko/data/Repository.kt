package com.example.tasko.data

import com.example.tasko.data.retrofit.ObavezaApi
import com.example.tasko.data.retrofit.models.ChatRequest
import com.example.tasko.data.retrofit.models.ChatResponse
import com.example.tasko.data.retrofit.models.ListTasks
import com.example.tasko.data.retrofit.models.MessageResponse
import com.example.tasko.data.retrofit.models.NewProject
import com.example.tasko.data.retrofit.models.Obaveza
import com.example.tasko.data.retrofit.models.ProjectList
import com.example.tasko.data.retrofit.models.TaskName
import com.example.tasko.data.retrofit.models.TasksListList
import com.example.tasko.data.retrofit.models.User
import com.example.tasko.data.retrofit.models.UserRequest
import retrofit2.http.Body
import javax.inject.Inject

class Repository @Inject constructor(
    private val obavezaApi: ObavezaApi,
) {
    suspend fun getObaveza(): List<Obaveza> = obavezaApi.getObaveza()

    suspend fun createObaveza(obaveza: Obaveza):Obaveza = obavezaApi.createObaveza(obaveza)

    suspend fun logIn(@Body userReq: UserRequest): MessageResponse = obavezaApi.logIn(userReq)

    suspend fun signUp(@Body user: User): MessageResponse = obavezaApi.signUp(user)

    suspend fun selectProjectUser(@Body userReq: UserRequest): ProjectList = obavezaApi.selectProjectUser(userReq)

    suspend fun createProject(@Body newPr: NewProject): MessageResponse = obavezaApi.createProject(newPr)
    suspend fun selectAllTasksProject(@Body newPr: NewProject): TasksListList = obavezaApi.selectAllTasksProject(newPr)

    suspend fun selectAllTasksOfTaskLists(@Body tn: TaskName): ListTasks = obavezaApi.selectAllTasksOfTaskLists(tn)

    suspend fun chat(@Body tn: ChatRequest): ChatResponse = obavezaApi.chat(tn)
}