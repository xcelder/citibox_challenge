package com.example.domain.entities

import com.example.domain.entities.Character

data class CharactersPage(
    val nextPage: Int?,
    val characters: List<Character>
)