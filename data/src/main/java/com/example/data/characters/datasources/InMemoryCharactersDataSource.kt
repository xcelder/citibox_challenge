package com.example.data.characters.datasources

import arrow.core.Either
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.error.SourceError

interface InMemoryCharactersDataSource {

    suspend fun getCharactersPage(page: Int): Either<SourceError, CharactersPage>

    suspend fun getCharacters(characterIds: List<Long>): Either<SourceError, List<Character>>

    suspend fun storeCharactersPage(page: Int, characters: List<Character>)

    suspend fun storeCharacters(characters: List<Character>)

    suspend fun isCharactersPageStored(page: Int): Boolean

    suspend fun isCharactersStored(characterIds: List<Long>): Boolean
}