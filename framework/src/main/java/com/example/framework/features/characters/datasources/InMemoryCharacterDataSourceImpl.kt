package com.example.framework.features.characters.datasources

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.error.DDBBError
import com.example.domain.error.SourceError
import com.example.framework.sources.db.daos.CharactersDao
import com.example.framework.sources.db.entities.*
import com.example.framework.sources.db.entities.toDomainCharacter

internal class InMemoryCharacterDataSourceImpl(
    private val charactersDao: CharactersDao
) : InMemoryCharactersDataSource {

    override suspend fun getCharactersPage(page: Int): Either<SourceError, CharactersPage> =
        runCatching {
            CharactersPage(
                nextPage = page + 1,
                characters = charactersDao.getCharactersPage(page).characters.map { it.toDomainCharacter() }
            )
        }.fold(
            onSuccess = { it.right() },
            onFailure = { DDBBError.left() }
        )

    override suspend fun getCharacters(characterIds: List<Long>): Either<SourceError, List<Character>> =
        runCatching { charactersDao.getCharacters(characterIds).map { it.toDomainCharacter() } }
            .fold(
                onSuccess = { it.right() },
                onFailure = { DDBBError.left() }
            )

    override suspend fun storeCharactersPage(page: Int, characters: List<Character>) {
        val dbCharacters = characters.map { it.toDbCharacter() }

        val dbCharactersPage = DbCharactersPage(
            page = DbPage(page = page),
            characters = dbCharacters
        )

        charactersDao.insertCharactersPage(dbCharactersPage)
    }

    override suspend fun storeCharacters(characters: List<Character>) {
        charactersDao.insertCharacters(characters.map { it.toDbCharacter() })
    }

    override suspend fun isCharactersPageStored(page: Int): Boolean = charactersDao.getPage(page).isNotEmpty()

    override suspend fun isCharactersStored(characterIds: List<Long>): Boolean =
        charactersDao.getCharactersCountIn(characterIds) == characterIds.count()
}
