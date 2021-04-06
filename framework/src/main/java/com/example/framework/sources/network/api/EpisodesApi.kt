package com.example.framework.sources.network.api

import com.example.framework.sources.network.models.NetworkEpisode
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesApi {

    @GET("/api/episode/{episodes}")
    suspend fun getEpisodes(@Path("episodes") episodes: String): List<NetworkEpisode>
}