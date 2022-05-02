package com.example.citiboxchallenge.presentation.di

import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListInteractors
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListViewModel
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersPaginationManager
import com.example.citiboxchallenge.presentation.router.CharactersRouter
import com.example.usecase.GetCharacterMeetUp
import com.example.usecase.GetCharacters
import org.koin.dsl.module

val viewModelModule = module {
    factory {
        CharactersListInteractors(
            GetCharacters(get()),
            GetCharacterMeetUp(get(), get())
        )
    }
    factory { params ->
        CharactersListViewModel(
            interactors = get(),
            charactersPaginationManager = CharactersPaginationManager(),
            router = CharactersRouter(params[0])
        )
    }
}