package com.example.usecase

import com.example.data.episodes.repository.EpisodesRepository


class GetEpisodes(private val episodesRepository: EpisodesRepository) {
    suspend operator fun invoke(episodeNumbers: List<Int>) = episodesRepository.getEpisodes(episodeNumbers)
}