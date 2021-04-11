package com.example.framework.sources.network.api

import com.example.framework.sources.network.models.CharactersResponse
import com.example.framework.sources.network.models.NetworkCharacter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("/api/character/")
    fun getCharactersPage(@Query("page") page: Int): Call<CharactersResponse>

    @GET("/api/character/{characterIds}")
    fun getCharacters(@Path("characterIds") characterIds: String): Call<List<NetworkCharacter>>
}