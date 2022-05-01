package com.example.framework.features.characters.datasources

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.error.NetworkError
import com.example.domain.error.SourceError
import com.example.framework.sources.network.api.CharactersApi
import com.example.framework.sources.network.models.toDomainCharacter
import retrofit2.awaitResponse
import java.lang.IllegalArgumentException

internal class NetworkCharactersDataSourceImpl(
    private val charactersApi: CharactersApi
) : NetworkCharactersDataSource {

    override suspend fun getCharactersPage(page: Int): Either<SourceError, CharactersPage> {
        return runCatching {
            val response = charactersApi.getCharactersPage(page).awaitResponse()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && !body.results.isNullOrEmpty()) {
                    CharactersPage(
                        body.info.next?.substringAfterLast("=")?.toIntOrNull(),
                        body.results.map { it.toDomainCharacter() }
                    )
                } else {
                    throw IllegalArgumentException("Response does not contains characters data")
                }
            } else {
                throw IllegalArgumentException("Response is not successful")
            }
        }.fold(
            onSuccess = { it.right() },
            onFailure = { NetworkError.left() }
        )
    }

    override suspend fun getCharacters(characterIds: List<String>): Either<SourceError, List<Character>> {
        return runCatching {
            val characterIdsString = characterIds.joinToString(",")
            val response = charactersApi.getCharacters(characterIdsString).awaitResponse()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && !body.isNullOrEmpty()) {
                    body.map { it.toDomainCharacter() }
                } else {
                    throw IllegalArgumentException("Response does not contains characters data")
                }
            } else {
                throw IllegalArgumentException("Response is not successful")
            }
        }.fold(
            onSuccess = { it.right() },
            onFailure = { NetworkError.left() }
        )
    }
}