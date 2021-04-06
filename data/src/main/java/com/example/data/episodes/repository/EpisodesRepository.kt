package com.example.data.episodes.repository

import com.example.domain.entities.Episode

interface EpisodesRepository {

    suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode>
}