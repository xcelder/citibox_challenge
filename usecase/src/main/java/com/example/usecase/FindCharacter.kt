package com.example.usecase

import com.example.data.characters.repository.CharactersRepository


class FindCharacter(private val repository: CharactersRepository) {
    suspend operator fun invoke(search: String) = repository.findCharacterByName(search)
}