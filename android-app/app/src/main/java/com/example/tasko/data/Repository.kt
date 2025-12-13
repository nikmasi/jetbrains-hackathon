package com.example.tasko.data

import com.example.tasko.data.retrofit.ObavezaApi
import com.example.tasko.data.retrofit.models.Obaveza
import javax.inject.Inject

class Repository @Inject constructor(
    private val obavezaApi: ObavezaApi,
) {
    suspend fun getObaveza(): List<Obaveza> = obavezaApi.getObaveza()

    suspend fun createObaveza(obaveza: Obaveza):Obaveza = obavezaApi.createObaveza(obaveza)




}