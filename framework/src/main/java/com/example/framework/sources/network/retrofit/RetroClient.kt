package com.example.framework.sources.network.retrofit

import com.example.framework.sources.network.api.CharactersApi
import com.example.framework.sources.network.api.EpisodesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://rickandmortyapi.com"

object RetroClient {

    private val client: Retrofit
        get() = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val charactersApi: CharactersApi
        get() = client.create(CharactersApi::class.java)

    val episodesApi: EpisodesApi
        get() = client.create(EpisodesApi::class.java)
}