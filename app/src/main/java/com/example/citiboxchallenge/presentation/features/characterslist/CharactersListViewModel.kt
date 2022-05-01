package com.example.citiboxchallenge.presentation.features.characterslist

import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.example.citiboxchallenge.presentation.router.CharactersRouter
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.usecase.GetCharacterMeetUp
import com.example.usecase.GetCharacters
import com.example.usecase.GetEpisodes
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class CharactersListViewModel(
    private val interactors: CharactersListInteractors,
    private val charactersPaginationManager: CharactersPaginationManager,
    private val router: CharactersRouter
) : MavericksViewModel<CharactersListState>(CharactersListState()) {

    fun loadNextCharactersPage() {
        val nextPage = charactersPaginationManager.data.nextPage
        if (nextPage != null) {
            loadCharactersPage(nextPage)
        }
    }

    fun onCharacterSelected(character: Character) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            interactors.getCharacterMeetUp.invoke(character)
                .fold(
                    ifLeft = { setState { copy(isLoading = false, isError = true) } },
                    ifRight = { meetUp ->
                        setState { copy(isLoading = false, isError = false) }
                        router.navigateToCharactersMeetUp(meetUp)
                    }
                )
        }
    }

    private fun loadCharactersPage(page: Int) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            interactors.getCharacters.invoke(page)
                .tap { notifyNextCharactersPage(it) }
                .map { it.characters }
                .fold(
                    ifLeft = { setState { copy(isLoading = false, isError = true) } },
                    ifRight = {
                        setState {
                            copy(isLoading = false, isError = false, characters = characters + it)
                        }
                    }
                )
        }
    }

    private fun notifyNextCharactersPage(charactersPage: CharactersPage) {
        with(charactersPaginationManager) {
            update(
                data.copy(
                    currentPage = data.nextPage ?: data.currentPage,
                    nextPage = charactersPage.nextPage
                )
            )
        }
    }

    companion object : MavericksViewModelFactory<CharactersListViewModel, CharactersListState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: CharactersListState
        ) = with((viewModelContext as FragmentViewModelContext).fragment) {
            val interactors = CharactersListInteractors(
                GetCharacters(get()),
                GetEpisodes(get()),
                GetCharacterMeetUp(get(), get())
            )
            CharactersListViewModel(
                interactors = interactors,
                charactersPaginationManager = CharactersPaginationManager(),
                router = CharactersRouter(findNavController())
            )
        }
    }
}
