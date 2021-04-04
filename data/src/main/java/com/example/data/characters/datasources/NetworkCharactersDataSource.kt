package com.example.data.characters.datasources

import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage

interface NetworkCharactersDataSource {

    suspend fun getCharactersPage(page: Int): CharactersPage

    suspend fun findCharacterByName(search: String): List<Character>
}