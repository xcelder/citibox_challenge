package com.example.citiboxchallenge.presentation.features.characterslist

import com.airbnb.mvrx.MavericksState
import com.example.domain.entities.Character

data class CharactersListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val characters: List<Character> = emptyList(),
) : MavericksState