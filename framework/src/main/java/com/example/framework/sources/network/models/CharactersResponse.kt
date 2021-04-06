package com.example.framework.sources.network.models

data class CharactersResponse (
    val info: Info,
    val results: List<NetworkCharacter>
)