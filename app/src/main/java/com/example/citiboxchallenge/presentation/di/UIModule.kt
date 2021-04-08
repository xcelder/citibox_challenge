package com.example.citiboxchallenge.presentation.di

import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListInteractors
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListViewModel
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersPaginationManager
import com.example.usecase.FindCharacter
import com.example.usecase.GetCharacters
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        val interactors = CharactersListInteractors(
            GetCharacters(get()),
            FindCharacter(get())
        )
        CharactersListViewModel(
            interactors = interactors,
            charactersPaginationManager = CharactersPaginationManager(),
            foregroundDispatcher = Dispatchers.Main,
            backgroundDispatcher = Dispatchers.IO
        )
    }
}