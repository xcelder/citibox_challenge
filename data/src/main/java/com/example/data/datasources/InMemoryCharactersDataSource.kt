package com.example.data.datasources

import com.example.domain.entities.Character

interface InMemoryCharactersDataSource {

    suspend fun getCharactersPage(page: Int): List<Character>

    suspend fun storeCharactersPage(page: Int, characters: List<Character>)
}