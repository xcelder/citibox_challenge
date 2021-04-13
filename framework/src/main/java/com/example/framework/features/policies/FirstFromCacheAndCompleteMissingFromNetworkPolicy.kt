package com.example.framework.features.policies

internal suspend fun <Entity, Param> getDataFirstFromCacheAndCompleteMissingFromNetwork(
    getFromCache: suspend () -> Entity,
    getMissingData: (Entity) -> List<Param>,
    storeInCache: suspend (Entity) -> Unit,
    getFromNetwork: suspend (List<Param>) -> Entity,
    mergeSources: (Entity, Entity) -> Entity
): Entity {
    val dataFromCache = getFromCache()

    val missingData = getMissingData(dataFromCache)
    val isCompletelyStored = missingData.isEmpty()

    return if (isCompletelyStored) {
        dataFromCache
    } else {
        val dataFromNetwork = getFromNetwork(missingData)
        storeInCache(dataFromNetwork)
        mergeSources(dataFromCache, dataFromNetwork)
    }
}