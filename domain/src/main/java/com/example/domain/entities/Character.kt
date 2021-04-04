package com.example.domain.entities

data class Character(
    val name: String,
    val imageUrl: String,
    val location: Location,
    val species: String,
    val type: String,
    val episode: List<String>
)