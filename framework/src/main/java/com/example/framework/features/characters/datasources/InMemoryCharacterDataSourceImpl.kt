package com.example.framework.features.characters.datasources

import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.framework.sources.db.daos.CharactersDao
import com.example.framework.sources.db.daos.SearchDao
import com.example.framework.sources.db.entities.*
import com.example.framework.sources.db.entities.toDomainCharacter

internal class InMemoryCharacterDataSourceImpl(
    private val charactersDao: CharactersDao,
    private val searchDao: SearchDao
) : InMemoryCharactersDataSource {

    override suspend fun getCharactersPage(page: Int): CharactersPage = CharactersPage(
        nextPage = page + 1,
        characters = charactersDao.getCharactersPage(page).characters.map { it.toDomainCharacter() }
    )

    override suspend fun findCharactersByName(search: String): List<Character> =
        charactersDao.findCharactersByName(search).map { it.toDomainCharacter() }

    override suspend fun storeCharactersPage(page: Int, characters: List<Character>) {
        val dbCharacters = characters.map { it.toDbCharacter() }

        val dbCharactersPage = DbCharactersPage(
            page = DbPage(page = page),
            characters = dbCharacters
        )

        charactersDao.insertCharactersPage(dbCharactersPage)
    }

    override suspend fun storeCharactersSearch(search: String, characters: List<Character>) {
        val dbCharacters = characters.map { it.toDbCharacter() }

        charactersDao.insertCharacters(dbCharacters)
        searchDao.insertSearch(DbSearch(search = search))
    }

    override suspend fun isCharactersPageStored(page: Int): Boolean = charactersDao.getPage(page).isNotEmpty()

    override suspend fun isSearchStored(search: String): Boolean = searchDao.getSearch(search).isNotEmpty()

}
