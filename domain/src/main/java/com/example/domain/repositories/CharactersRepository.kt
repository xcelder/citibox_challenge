package com.example.domain.repositories

import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage

interface CharactersRepository {

    suspend fun getCharactersPage(page: Int): CharactersPage

    suspend fun findCharacterByName(search: String): List<Character>
}