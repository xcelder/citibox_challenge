package com.example.framework.episodes

import com.example.data.episodes.datasources.NetworkEpisodesDataSource
import com.example.domain.entities.Episode
import com.example.framework.network.api.EpisodesApi
import com.example.framework.network.models.toDomainEpisode

internal class NetworkEpisodesDataSourceImpl(
    private val episodesApi: EpisodesApi
) : NetworkEpisodesDataSource {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode> {
        val result = episodesApi.getEpisodes(episodeNumbers.joinToString(separator = ","))

        return result.map { it.toDomainEpisode() }
    }
}