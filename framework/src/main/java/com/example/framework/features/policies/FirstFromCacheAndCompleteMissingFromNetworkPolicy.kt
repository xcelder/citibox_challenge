package com.example.framework.features.policies

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import arrow.core.zip
import com.example.domain.error.SourceError

internal suspend fun <Entity, Param> getDataFirstFromCacheAndCompleteMissingFromNetwork(
    getFromCache: suspend () -> Either<SourceError, Entity>,
    getMissingData: (Entity) -> List<Param>,
    storeInCache: suspend (Entity) -> Unit,
    getFromNetwork: suspend (List<Param>) -> Either<SourceError, Entity>,
    mergeSources: (Entity, Entity) -> Entity
): Either<SourceError, Entity> {
    return getFromCache()
        .flatMap { dataFromCache ->
            val missingData = getMissingData(dataFromCache)

            if (missingData.isEmpty()) {
                dataFromCache.right()
            } else {
                getFromNetwork(missingData)
                    .tap { storeInCache(it) }
                    .zip(dataFromCache.right())
                    .map { (network, cache) -> mergeSources(cache, network) }
            }
        }
}