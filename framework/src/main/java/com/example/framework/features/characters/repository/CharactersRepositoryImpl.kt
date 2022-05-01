package com.example.framework.features.characters.repository

import arrow.core.Either
import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.data.characters.repository.CharactersRepository
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.error.SourceError
import com.example.framework.features.policies.getDataFirstFromCache
import com.example.framework.features.policies.getDataFirstFromCacheAndCompleteMissingFromNetwork

internal class CharactersRepositoryImpl(
    private val inMemoryCharactersDataSource: InMemoryCharactersDataSource,
    private val networkCharactersDataSource: NetworkCharactersDataSource
) : CharactersRepository {

    override suspend fun getCharactersPage(page: Int): Either<SourceError, CharactersPage> = getDataFirstFromCache(
        isStored = { inMemoryCharactersDataSource.isCharactersPageStored(page) },
        getFromCache = { inMemoryCharactersDataSource.getCharactersPage(page) },
        storeInCache = { inMemoryCharactersDataSource.storeCharactersPage(page, it.characters) },
        getFromNetwork = { networkCharactersDataSource.getCharactersPage(page) }
    )

    override suspend fun getCharacters(characterIds: List<String>): Either<SourceError, List<Character>> {
        val characterIdsLong = characterIds.mapNotNull { it.toLongOrNull() }
        return getDataFirstFromCacheAndCompleteMissingFromNetwork(
            getFromCache = { inMemoryCharactersDataSource.getCharacters(characterIdsLong) },
            getMissingData = { dataFromCache ->
                val characterIdsFromCache = dataFromCache.map { it.id.toString() }
                characterIds.mapNotNull { if (it !in characterIdsFromCache) it else null }
            },
            storeInCache = { inMemoryCharactersDataSource.storeCharacters(it) },
            getFromNetwork = { networkCharactersDataSource.getCharacters(it) },
            mergeSources = { cache, network -> cache + network }
        )
    }
}
