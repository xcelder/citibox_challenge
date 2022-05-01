package com.example.framework.features.episodes.repository

import arrow.core.Either
import com.example.data.episodes.datasources.InMemoryEpisodesDataSource
import com.example.data.episodes.datasources.NetworkEpisodesDataSource
import com.example.data.episodes.repository.EpisodesRepository
import com.example.domain.entities.Episode
import com.example.domain.error.SourceError
import com.example.framework.features.policies.getDataFirstFromCacheAndCompleteMissingFromNetwork

internal class EpisodesRepositoryImpl(
    private val inMemoryEpisodesDataSource: InMemoryEpisodesDataSource,
    private val networkEpisodesDataSource: NetworkEpisodesDataSource
) : EpisodesRepository {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): Either<SourceError, List<Episode>> =
        getDataFirstFromCacheAndCompleteMissingFromNetwork(
            getFromCache = { inMemoryEpisodesDataSource.getEpisodes(episodeNumbers) },
            getMissingData = { dataFromCache ->
                val episodeIdsFromCache = dataFromCache.map { it.id }
                episodeNumbers.mapNotNull { if (it !in episodeIdsFromCache) it else null }
            },
            storeInCache = { inMemoryEpisodesDataSource.storeEpisodes(it) },
            getFromNetwork = { networkEpisodesDataSource.getEpisodes(it) },
            mergeSources = { cache, network -> cache + network }
        )
}