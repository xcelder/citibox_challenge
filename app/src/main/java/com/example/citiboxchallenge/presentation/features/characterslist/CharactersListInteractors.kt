package com.example.citiboxchallenge.presentation.features.characterslist

import com.example.usecase.GetCharacterMeetUp
import com.example.usecase.GetCharacters
import com.example.usecase.GetEpisodes

data class CharactersListInteractors(
    val getCharacters: GetCharacters,
    val getEpisodes: GetEpisodes,
    val getCharacterMeetUp: GetCharacterMeetUp
)