package com.example.data.datasources

import com.example.domain.entities.Episode

interface InMemoryEpisodesDataSource {

    suspend fun getEpisodes(episodeNumbers: List<String>): List<Episode>

    suspend fun storeEpisodes(episodes: List<Episode>)
}