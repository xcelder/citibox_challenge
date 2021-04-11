package com.example.usecase

import com.example.data.characters.repository.CharactersRepository
import com.example.data.episodes.repository.EpisodesRepository
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersMeetUp
import com.example.domain.entities.Episode
import java.util.*

class GetCharacterMeetUp(
    private val charactersRepository: CharactersRepository,
    private val episodesRepository: EpisodesRepository
) {

    private data class CharacterWithEpisodes(
        val character: Character,
        val matchEpisodesCount: Int,
        val olderEpisodeTime: Long,
        val newerEpisodeTime: Long
    )

    private val betterMatchConditions: List<(CharacterWithEpisodes, CharacterWithEpisodes) -> Boolean> =
        listOf(
            { old, new -> new.matchEpisodesCount > old.matchEpisodesCount },
            { old, new -> new.matchEpisodesCount == old.matchEpisodesCount && new.olderEpisodeTime < old.olderEpisodeTime },
            { old, new ->
                new.matchEpisodesCount == old.matchEpisodesCount &&
                    new.olderEpisodeTime == old.olderEpisodeTime &&
                    new.newerEpisodeTime < old.newerEpisodeTime
            },
            { old, new -> new.matchEpisodesCount == old.matchEpisodesCount &&
                    new.olderEpisodeTime == old.olderEpisodeTime &&
                    new.newerEpisodeTime == old.newerEpisodeTime &&
                    new.character.id < old.character.id
            }
        )

    suspend operator fun invoke(character: Character): CharactersMeetUp {
        val episodes = episodesRepository.getEpisodes(character.episode)

        val characterIds = getCharactersFromEpisodesExcludingCharacter(episodes, character)

        val possibleMatchCharacters = charactersRepository.getCharacters(characterIds)
            .map {
                val matchEpisodes = episodes.filter { episode -> it.episode.contains(episode.id) }
                val (olderEpisodeTime, newerEpisodeTime) = matchEpisodes.olderAndNewerTimes
                CharacterWithEpisodes(it, matchEpisodes.count(), olderEpisodeTime, newerEpisodeTime)
            }
            .sortedWith { old, new -> if (betterMatchConditions.any { it(old, new) }) 1 else -1 }

        return possibleMatchCharacters.first().let { characterMatch ->
            CharactersMeetUp(
                characters = character to characterMatch.character,
                location = character.location,
                episodesTogether = characterMatch.matchEpisodesCount,
                firstMeet = Date(characterMatch.olderEpisodeTime),
                lastMeet = Date(characterMatch.newerEpisodeTime)
            )
        }
    }

    private fun getCharactersFromEpisodesExcludingCharacter(
        episodes: List<Episode>,
        character: Character
    ): List<String> = mutableSetOf<String>().apply {
        episodes.forEach { episode ->
            episode.characters.forEach { characterId ->
                if (characterId != character.id.toString()) {
                    add(characterId)
                }
            }
        }
    }.toList()

    private fun List<CharacterWithEpisodes>.findMatch(character: Character): CharacterWithEpisodes? {
        var possibleMatch: CharacterWithEpisodes? = null

        forEach { candidate ->
            val possibleMatchVal = possibleMatch
            if (candidate.character.location.url == character.location.url) {
                if (possibleMatchVal == null || betterMatchConditions.any { it(possibleMatchVal, candidate) }) {
                    possibleMatch = candidate
                } else {
                    return possibleMatchVal
                }
            }
        }

        return possibleMatch
    }

    private val List<Episode>.olderAndNewerTimes: Pair<Long, Long>
        get() {
            var older: Long = Long.MAX_VALUE
            var newer: Long = 0

            forEach {
                if (older > it.airDate.time) {
                    older = it.airDate.time
                }

                if (newer < it.airDate.time) {
                    newer = it.airDate.time
                }
            }

            return older to newer
        }
}