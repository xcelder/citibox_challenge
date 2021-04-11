package com.example.data.characters.datasources

import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage

interface InMemoryCharactersDataSource {

    suspend fun getCharactersPage(page: Int): CharactersPage

    suspend fun getCharacters(characterIds: List<Long>): List<Character>

    suspend fun storeCharactersPage(page: Int, characters: List<Character>)

    suspend fun storeCharacters(characters: List<Character>)

    suspend fun isCharactersPageStored(page: Int): Boolean

    suspend fun isCharactersStored(characterIds: List<Long>): Boolean
}