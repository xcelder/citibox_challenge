package com.example.data.episodes.repository

import arrow.core.Either
import com.example.domain.entities.Episode
import com.example.domain.error.SourceError

interface EpisodesRepository {

    suspend fun getEpisodes(episodeNumbers: List<Int>): Either<SourceError, List<Episode>>
}