package com.example.data.characters.repository

import arrow.core.Either
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.error.SourceError

interface CharactersRepository {

    suspend fun getCharactersPage(page: Int): Either<SourceError, CharactersPage>

    suspend fun getCharacters(characterIds: List<String>): Either<SourceError, List<Character>>
}