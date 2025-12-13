package com.example.tasko.data.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.tasko.data.retrofit.models.Obaveza

const val BASE_URL = "http://10.0.2.2:8080/"

interface ObavezaApi {
    @GET("obaveza")
    suspend fun getObaveza(): List<Obaveza>

    @POST("obaveza")
    suspend fun createObaveza(@Body obaveza: Obaveza):Obaveza
    /*
    @POST("dohvatiObaveza")
    suspend fun dohvatiObaveza(@Body id: IdObaveza): Obaveza

    @POST("oznaciObaveza")
    suspend fun oznaciObaveza(@Body id: IdObaveza): Obaveza
    */

}
