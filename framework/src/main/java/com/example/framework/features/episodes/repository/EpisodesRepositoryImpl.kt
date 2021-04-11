package com.example.framework.features.episodes.repository

import com.example.data.episodes.datasources.InMemoryEpisodesDataSource
import com.example.data.episodes.datasources.NetworkEpisodesDataSource
import com.example.data.episodes.repository.EpisodesRepository
import com.example.domain.entities.Episode

internal class EpisodesRepositoryImpl(
    private val inMemoryEpisodesDataSource: InMemoryEpisodesDataSource,
    private val networkEpisodesDataSource: NetworkEpisodesDataSource
) : EpisodesRepository {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode> {
        val episodesFromCache = inMemoryEpisodesDataSource.getEpisodes(episodeNumbers)
        val episodeIdsFromCache = episodesFromCache.map { it.id }

            val episodesNotRetrieved = episodeNumbers.mapNotNull { episodeNumber ->
            if (episodeNumber !in episodeIdsFromCache) episodeNumber
            else null
        }

        val areAllCompletelyStored = episodesNotRetrieved.isEmpty()


        return if (areAllCompletelyStored) {
            episodesFromCache
        } else {
            val episodesFromNetwork = networkEpisodesDataSource.getEpisodes(episodesNotRetrieved)
            inMemoryEpisodesDataSource.storeEpisodes(episodesFromNetwork)
            episodesFromCache + episodesFromNetwork
        }
    }
}