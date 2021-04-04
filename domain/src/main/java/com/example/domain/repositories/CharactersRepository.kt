package com.example.domain.repositories

import com.example.domain.entities.Character

interface CharactersRepository {

    suspend fun getCharactersPage(page: Int): List<Character>

    suspend fun findCharacterByName(search: String): List<Character>
}