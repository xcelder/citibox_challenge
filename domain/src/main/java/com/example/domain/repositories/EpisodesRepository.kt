package com.example.domain.repositories

import com.example.domain.entities.Episode

interface EpisodesRepository {

    suspend fun getEpisodes(episodeNumbers: List<Int>): List<Episode>
}