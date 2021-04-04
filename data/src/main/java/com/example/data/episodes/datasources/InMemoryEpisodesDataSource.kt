package com.example.data.episodes.datasources

import com.example.domain.entities.Episode

interface InMemoryEpisodesDataSource {

    suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode>

    suspend fun storeEpisodes(episodes: List<Episode>)
}