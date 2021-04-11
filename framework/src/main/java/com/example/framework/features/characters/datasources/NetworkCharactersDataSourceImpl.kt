package com.example.framework.features.characters.datasources

import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.framework.sources.network.api.CharactersApi
import com.example.framework.sources.network.models.toDomainCharacter
import retrofit2.awaitResponse
import java.lang.IllegalArgumentException

internal class NetworkCharactersDataSourceImpl(
    private val charactersApi: CharactersApi
) : NetworkCharactersDataSource {

    override suspend fun getCharactersPage(page: Int): CharactersPage {
        val response = charactersApi.getCharactersPage(page).awaitResponse()

        return if (response.isSuccessful) {
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
    }

    override suspend fun getCharacters(characterIds: List<String>): List<Character> {
        val characterIdsString = characterIds.joinToString(",")
        val response = charactersApi.getCharacters(characterIdsString).awaitResponse()

        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null && !body.isNullOrEmpty()) {
                body.map { it.toDomainCharacter() }
            } else {
                throw IllegalArgumentException("Response does not contains characters data")
            }
        } else {
            throw IllegalArgumentException("Response is not successful")
        }
    }
}