package com.example.framework.sources.network.api

import com.example.framework.sources.network.models.NetworkEpisode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesApi {

    @GET("/api/episode/{episodes}")
    fun getEpisodes(@Path("episodes") episodes: String): Call<List<NetworkEpisode>>
}