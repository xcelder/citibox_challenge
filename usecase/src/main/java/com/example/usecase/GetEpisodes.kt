package com.example.usecase

import com.example.domain.repositories.EpisodesRepository

class GetEpisodes(private val episodesRepository: EpisodesRepository) {
    suspend operator fun invoke(episodeNumbers: List<String>) = episodesRepository.getEpisodes(episodeNumbers)
}