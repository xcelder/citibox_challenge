package com.example.framework.sources.network.api

import com.example.framework.sources.network.models.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("/api/character/")
    fun getCharactersPage(@Query("page") page: Int): Call<CharactersResponse>

    @GET("/api/character/")
    fun findCharactersByName(@Query("name") name: String): Call<CharactersResponse>
}