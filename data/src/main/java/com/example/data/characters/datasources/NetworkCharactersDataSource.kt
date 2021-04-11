package com.example.data.characters.datasources

import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage

interface NetworkCharactersDataSource {

    suspend fun getCharactersPage(page: Int): CharactersPage

    suspend fun getCharacters(characterIds: List<String>): List<Character>
}