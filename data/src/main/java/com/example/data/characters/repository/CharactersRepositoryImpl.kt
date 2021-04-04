package com.example.data.characters.repository

import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.data.policies.getDataFirstFromCache
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.repositories.CharactersRepository

internal class CharactersRepositoryImpl(
    private val inMemoryCharactersDataSource: InMemoryCharactersDataSource,
    private val networkCharactersDataSource: NetworkCharactersDataSource
) : CharactersRepository {

    override suspend fun getCharactersPage(page: Int): CharactersPage = getDataFirstFromCache(
        isStored = { inMemoryCharactersDataSource.isCharactersPageStored(page) },
        getFromCache = { inMemoryCharactersDataSource.getCharactersPage(page) },
        storeInCache = { inMemoryCharactersDataSource.storeCharactersPage(page, it.characters) },
        getFromNetwork = { networkCharactersDataSource.getCharactersPage(page) }
    )

    override suspend fun findCharacterByName(search: String): List<Character> =
        getDataFirstFromCache(
            isStored = { inMemoryCharactersDataSource.isSearchStored(search) },
            getFromCache = { inMemoryCharactersDataSource.findCharactersByName(search) },
            storeInCache = { inMemoryCharactersDataSource.storeCharactersSearch(search, it) },
            getFromNetwork = { networkCharactersDataSource.findCharacterByName(search) }
        )
}