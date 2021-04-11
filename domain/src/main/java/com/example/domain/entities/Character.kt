package com.example.domain.entities

import java.io.Serializable

data class Character(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val location: Location,
    val species: String,
    val type: String,
    val episode: List<Int>
): Serializable