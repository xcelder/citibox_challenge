package com.example.data.characters.datasources

import com.example.domain.entities.Character

interface InMemoryCharactersDataSource {

    suspend fun getCharactersPage(page: Int): List<Character>

    suspend fun findCharactersByName(search: String): List<Character>

    suspend fun storeCharactersPage(page: Int, characters: List<Character>)

    suspend fun storeCharactersSearch(search: String, characters: List<Character>)

    suspend fun isCharactersPageStored(page: Int): Boolean

    suspend fun isSearchStored(search: String): Boolean
}