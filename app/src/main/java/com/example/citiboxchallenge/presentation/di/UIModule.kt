package com.example.citiboxchallenge.presentation.di

import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListInteractors
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListViewModel
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersPaginationManager
import com.example.citiboxchallenge.presentation.router.CharactersRouter
import com.example.usecase.GetCharacterMeetUp
import com.example.usecase.GetCharacters
import com.example.usecase.GetEpisodes
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { parameters ->
        val interactors = CharactersListInteractors(
            GetCharacters(get()),
            GetEpisodes(get()),
            GetCharacterMeetUp(get(), get())
        )
        CharactersListViewModel(
            interactors = interactors,
            charactersPaginationManager = CharactersPaginationManager(),
            router = CharactersRouter(parameters.get()),
            foregroundDispatcher = Dispatchers.Main,
            backgroundDispatcher = Dispatchers.IO
        )
    }
}