package com.example.usecase

import com.example.domain.repositories.CharactersRepository

class GetCharacters(private val repository: CharactersRepository) {
    suspend operator fun invoke(page: Int) = repository.getCharactersPage(page)
}