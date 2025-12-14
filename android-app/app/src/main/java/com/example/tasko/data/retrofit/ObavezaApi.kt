package com.example.tasko.data.retrofit

import com.example.tasko.data.retrofit.models.ListTasks
import com.example.tasko.data.retrofit.models.MessageResponse
import com.example.tasko.data.retrofit.models.NewProject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.tasko.data.retrofit.models.Obaveza
import com.example.tasko.data.retrofit.models.ProjectList
import com.example.tasko.data.retrofit.models.TaskName
import com.example.tasko.data.retrofit.models.TasksListList
import com.example.tasko.data.retrofit.models.User
import com.example.tasko.data.retrofit.models.UserRequest

const val BASE_URL = "http://10.0.2.2:8080/"

interface ObavezaApi {
    @GET("obaveza")
    suspend fun getObaveza(): List<Obaveza>

    @POST("obaveza")
    suspend fun createObaveza(@Body obaveza: Obaveza):Obaveza


    @POST("logIn")
    suspend fun logIn(@Body userReq: UserRequest): MessageResponse

    @POST("signUp")
    suspend fun signUp(@Body user: User): MessageResponse

    @POST("selectProjectUser")
    suspend fun selectProjectUser(@Body userReq: UserRequest): ProjectList

    @POST("createProject")
    suspend fun createProject(@Body newPr: NewProject): MessageResponse

    @POST("selectAllTasksProject")
    suspend fun selectAllTasksProject(@Body newPr: NewProject): TasksListList

    @POST("selectAllTasksOfTaskLists")
    suspend fun selectAllTasksOfTaskLists(@Body tn: TaskName): ListTasks
}
