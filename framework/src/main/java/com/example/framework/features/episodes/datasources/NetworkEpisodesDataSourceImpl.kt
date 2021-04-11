package com.example.framework.features.episodes.datasources

import com.example.data.episodes.datasources.NetworkEpisodesDataSource
import com.example.domain.entities.Episode
import com.example.framework.sources.network.api.EpisodesApi
import com.example.framework.sources.network.models.toDomainEpisode
import retrofit2.awaitResponse

internal class NetworkEpisodesDataSourceImpl(
    private val episodesApi: EpisodesApi
) : NetworkEpisodesDataSource {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode> {
        val response = episodesApi.getEpisodes(episodeNumbers.joinToString(separator = ",")).awaitResponse()

        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.isNotEmpty()) {
                body.map { it.toDomainEpisode() }
            } else {
                throw IllegalArgumentException("Episodes response is null or empty")
            }
        } else {
            throw IllegalArgumentException("Episodes response is not successful")
        }
    }
}