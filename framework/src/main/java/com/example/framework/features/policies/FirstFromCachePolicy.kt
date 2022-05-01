package com.example.framework.features.policies

import arrow.core.Either
import com.example.domain.error.SourceError

internal suspend fun <Entity> getDataFirstFromCache(
    isStored: suspend () -> Boolean,
    getFromCache: suspend () -> Either<SourceError, Entity>,
    storeInCache: suspend (Entity) -> Unit,
    getFromNetwork: suspend () -> Either<SourceError, Entity>
) =
    if (isStored()) {
        getFromCache()
    } else {
        getFromNetwork().tap { storeInCache(it) }
    }