package com.example.data.episodes.repository

import com.example.data.episodes.datasources.InMemoryEpisodesDataSource
import com.example.data.episodes.datasources.NetworkEpisodesDataSource
import com.example.domain.entities.Episode
import com.example.domain.repositories.EpisodesRepository

internal class EpisodesRepositoryImpl(
    private val inMemoryEpisodesDataSource: InMemoryEpisodesDataSource,
    private val networkEpisodesDataSource: NetworkEpisodesDataSource
) : EpisodesRepository {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode> {
        val episodesFromCache = inMemoryEpisodesDataSource.getEpisodes(episodeNumbers)

        val episodesNotRetrieved = episodesFromCache.mapNotNull {
            if (it.id !in episodeNumbers) it.id
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