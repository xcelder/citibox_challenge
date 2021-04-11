package com.example.data.characters.repository

import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage

interface CharactersRepository {

    suspend fun getCharactersPage(page: Int): CharactersPage

    suspend fun getCharacters(characterIds: List<String>): List<Character>
}