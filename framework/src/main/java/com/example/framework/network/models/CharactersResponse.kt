package com.example.framework.network.models

data class CharactersResponse (
    val info: Info,
    val results: List<NetworkCharacter>
)