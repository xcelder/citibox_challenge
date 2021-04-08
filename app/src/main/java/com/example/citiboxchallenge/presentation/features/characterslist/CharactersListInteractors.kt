package com.example.citiboxchallenge.presentation.features.characterslist

import com.example.usecase.FindCharacter
import com.example.usecase.GetCharacters

data class CharactersListInteractors(
    val getCharacters: GetCharacters,
    val findCharacter: FindCharacter
)