package com.example.data.datasources

import com.example.domain.entities.Character

interface NetworkCharactersDataSource {

    suspend fun getCharactersPage(page: Int): List<Character>

    suspend fun findCharacterByName(search: String): List<Character>
}