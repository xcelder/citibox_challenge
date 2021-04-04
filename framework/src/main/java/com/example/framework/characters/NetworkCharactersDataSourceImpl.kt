package com.example.framework.characters

import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.framework.network.api.CharactersApi
import com.example.framework.network.models.toDomainCharacter
import java.lang.IllegalArgumentException

internal class NetworkCharactersDataSourceImpl(
    private val charactersApi: CharactersApi
) : NetworkCharactersDataSource {

    override suspend fun getCharactersPage(page: Int): CharactersPage {
        val response = charactersApi.getCharactersPage(page)

        return if (!response.results.isNullOrEmpty()) {
            CharactersPage(
                response.info.next.substringAfterLast("/").toIntOrNull(),
                response.results.map { it.toDomainCharacter() }
            )
        } else {
            throw IllegalArgumentException("Response does not contains characters data")
        }
    }

    override suspend fun findCharacterByName(search: String): List<Character> {
        val response = charactersApi.findCharactersByName(search)

        return if (!response.results.isNullOrEmpty()) {
            response.results.map { it.toDomainCharacter() }
        } else {
            throw IllegalArgumentException("Response does not contains characters data")
        }
    }
}