package com.example.data.episodes.datasources

import arrow.core.Either
import com.example.domain.entities.Episode
import com.example.domain.error.SourceError

interface NetworkEpisodesDataSource {

    suspend fun getEpisodes(episodeNumbers: List<Int>): Either<SourceError, List<Episode>>
}