package com.example.framework.network.api

import com.example.domain.entities.Episode
import com.example.framework.network.models.NetworkEpisode
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesApi {

    @GET("/api/episode/{episodes}")
    suspend fun getEpisodes(@Path("episodes") episodes: String): List<NetworkEpisode>
}