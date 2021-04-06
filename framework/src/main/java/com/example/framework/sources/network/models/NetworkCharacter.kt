package com.example.framework.sources.network.models

import com.example.domain.entities.Character

data class NetworkCharacter (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

fun NetworkCharacter.toDomainCharacter() = Character(
    name = name,
    episode = episode.map { it.substringAfterLast("/")},
    imageUrl = image,
    location = com.example.domain.entities.Location(location.name, location.url),
    species = species,
    type = type
)