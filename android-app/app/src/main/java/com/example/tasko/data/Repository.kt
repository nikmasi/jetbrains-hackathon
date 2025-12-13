package com.example.tasko.data

import com.example.tasko.data.retrofit.ObavezaApi
import com.example.tasko.data.retrofit.models.MessageResponse
import com.example.tasko.data.retrofit.models.Obaveza
import com.example.tasko.data.retrofit.models.User
import com.example.tasko.data.retrofit.models.UserRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class Repository @Inject constructor(
    private val obavezaApi: ObavezaApi,
) {
    suspend fun getObaveza(): List<Obaveza> = obavezaApi.getObaveza()

    suspend fun createObaveza(obaveza: Obaveza):Obaveza = obavezaApi.createObaveza(obaveza)

    suspend fun logIn(@Body userReq: UserRequest): MessageResponse = obavezaApi.logIn(userReq)

    suspend fun signUp(@Body user: User): MessageResponse = obavezaApi.signUp(user)



}