package com.example.data.datasources

import com.example.domain.entities.Episode

interface NetworkEpisodesDataSource {

    suspend fun getEpisodes(episodeNumbers: List<String>): List<Episode>
}