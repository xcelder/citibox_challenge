package com.example.data.repositories

import com.example.domain.entities.Character
import com.example.domain.repositories.CharactersRepository

class CharactersRepositoryImpl : CharactersRepository {
    override suspend fun getCharactersPage(page: Int): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun findCharacterByName(search: String): List<Character> {
        TODO("Not yet implemented")
    }
}