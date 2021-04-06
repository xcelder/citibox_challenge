package com.example.framework.features.characters.policies

internal suspend fun <Entity> getDataFirstFromCache(
    isStored: suspend () -> Boolean,
    getFromCache: suspend () -> Entity,
    storeInCache: suspend (Entity) -> Unit,
    getFromNetwork: suspend () -> Entity
) =
    if (isStored()) {
        getFromCache()
    } else {
        getFromNetwork().also { storeInCache(it) }
    }