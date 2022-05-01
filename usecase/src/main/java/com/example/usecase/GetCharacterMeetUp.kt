package com.example.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.data.characters.repository.CharactersRepository
import com.example.data.episodes.repository.EpisodesRepository
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersMeetUp
import com.example.domain.entities.Episode
import com.example.domain.error.SourceError
import java.util.*

class GetCharacterMeetUp(
    private val charactersRepository: CharactersRepository,
    private val episodesRepository: EpisodesRepository
) {

    private data class CharacterWithEpisodes(
        val character: Character,
        val matchEpisodesCount: Int,
        val oldestEpisodeTime: Long,
        val newestEpisodeTime: Long
    )

    private val betterMatchConditions: List<(CharacterWithEpisodes, CharacterWithEpisodes) -> Boolean> =
        listOf(
            { old, new -> new.matchEpisodesCount > old.matchEpisodesCount },
            { old, new ->
                new.matchEpisodesCount == old.matchEpisodesCount &&
                        new.oldestEpisodeTime < old.oldestEpisodeTime
            },
            { old, new ->
                new.matchEpisodesCount == old.matchEpisodesCount &&
                        new.oldestEpisodeTime == old.oldestEpisodeTime &&
                        new.newestEpisodeTime < old.newestEpisodeTime
            },
            { old, new ->
                new.matchEpisodesCount == old.matchEpisodesCount &&
                        new.oldestEpisodeTime == old.oldestEpisodeTime &&
                        new.newestEpisodeTime == old.newestEpisodeTime &&
                        new.character.id < old.character.id
            }
        )

    suspend operator fun invoke(character: Character): Either<SourceError, CharactersMeetUp> {
        return episodesRepository.getEpisodes(character.episode)
            .map { getCharactersFromEpisodesExcludingCharacter(it, character) to it }
            .flatMap { (characterIds, episodes) ->
                charactersRepository.getCharacters(characterIds)
                    .map { characters -> characters to episodes }
            }.map { (characters, episodes) ->
                characters.mapNotNull {
                    if (character.location.url == it.location.url) {
                        val matchEpisodes =
                            episodes.filter { episode -> it.episode.contains(episode.id) }
                        val (olderEpisodeTime, newerEpisodeTime) = matchEpisodes.oldestAndNewestTimes
                        CharacterWithEpisodes(
                            it,
                            matchEpisodes.count(),
                            olderEpisodeTime,
                            newerEpisodeTime
                        )
                    } else null
                }
                    .sortedWith { old, new -> if (betterMatchConditions.any { it(old, new) }) 1 else -1 }
            }.map { possibleMatchCharacters ->
                possibleMatchCharacters.first().let { characterMatch ->
                    CharactersMeetUp(
                        characters = character to characterMatch.character,
                        location = character.location,
                        episodesTogether = characterMatch.matchEpisodesCount,
                        firstMeet = Date(characterMatch.oldestEpisodeTime),
                        lastMeet = Date(characterMatch.newestEpisodeTime)
                    )
                }
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

    private val List<Episode>.oldestAndNewestTimes: Pair<Long, Long>
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