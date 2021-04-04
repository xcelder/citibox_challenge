package com.example.domain.entities

import java.util.*

data class Episode(
    val id: Int,
    val airDate: Date,
    val characters: List<String>
)