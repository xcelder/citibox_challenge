package com.example.framework.episodes

import com.example.data.episodes.datasources.InMemoryEpisodesDataSource
import com.example.domain.entities.Episode
import com.example.framework.db.daos.EpisodesDao
import com.example.framework.db.entities.dbEpisodeFromDomainEpisode
import com.example.framework.db.entities.toDomainEpisode

internal class InMemoryEpisodesDataSourceImpl(
    private val episodesDao: EpisodesDao
) : InMemoryEpisodesDataSource {

    override suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode> =
        episodesDao.getEpisodesByNumber(episodeNumbers).map { it.toDomainEpisode() }

    override suspend fun storeEpisodes(episodes: List<Episode>) {
        val dbEpisodes = episodes.map { dbEpisodeFromDomainEpisode(it) }

        episodesDao.insertEpisodes(dbEpisodes)
    }
}
