package com.example.data.policies

suspend fun <Entity> getDataFirstFromCache(
    isStored: suspend () -> Boolean,
    getFromCache: suspend () -> List<Entity>,
    storeInCache: suspend (List<Entity>) -> Unit,
    getFromNetwork: suspend () -> List<Entity>
) =
    if (isStored()) {
        getFromCache()
    } else {
        getFromNetwork().also { storeInCache(it) }
    }