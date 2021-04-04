package com.example.data.episodes.datasources

import com.example.domain.entities.Episode

interface NetworkEpisodesDataSource {

    suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode>
}