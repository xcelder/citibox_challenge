package com.example.framework.features.episodes.datasources

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.data.episodes.datasources.InMemoryEpisodesDataSource
import com.example.domain.entities.Episode
import com.example.domain.error.DDBBError
import com.example.domain.error.SourceError
import com.example.framework.sources.db.daos.EpisodesDao
import com.example.framework.sources.db.entities.dbEpisodeFromDomainEpisode
import com.example.framework.sources.db.entities.toDomainEpisode

internal class InMemoryEpisodesDataSourceImpl(
    private val episodesDao: EpisodesDao
) : InMemoryEpisodesDataSource {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): Either<SourceError, List<Episode>> =
        runCatching { episodesDao.getEpisodesByNumber(episodeNumbers).map { it.toDomainEpisode() } }
            .fold(
                onSuccess = { it.right() },
                onFailure = { DDBBError.left() }
            )

    override suspend fun storeEpisodes(episodes: List<Episode>) {
        val dbEpisodes = episodes.map { dbEpisodeFromDomainEpisode(it) }

        episodesDao.insertEpisodes(dbEpisodes)
    }
}
