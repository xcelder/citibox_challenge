package com.example.framework.network.api

import com.example.framework.network.models.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("/api/character/")
    suspend fun getCharactersPage(@Query("page") page: Int): CharactersResponse

    @GET("/api/character/")
    suspend fun findCharactersByName(@Query("name") name: String): CharactersResponse
}