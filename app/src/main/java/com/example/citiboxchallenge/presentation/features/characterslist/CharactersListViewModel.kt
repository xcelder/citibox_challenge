package com.example.citiboxchallenge.presentation.features.characterslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CharactersListViewModel(
    private val interactors: CharactersListInteractors,
    private val charactersPaginationManager: CharactersPaginationManager,
    private val foregroundDispatcher: CoroutineContext,
    private val backgroundDispatcher: CoroutineContext
) : ViewModel() {

    val charactersListStateFlow: StateFlow<CharactersListState>
        get() = _charactersListStateFlow

    val characters: StateFlow<List<Character>>
        get() = _characters

    private val _charactersListStateFlow = MutableStateFlow(CharactersListState.Ready)

    private val _characters = MutableStateFlow<List<Character>>(emptyList())

    fun loadNextCharactersPage() {
        with(charactersPaginationManager) {
            val nextPage = data.nextPage
            if (nextPage != null) {
                setPaginating(true)
                loadCharactersPage(nextPage)
            }
        }
    }

    private fun loadCharactersPage(page: Int) {
        viewModelScope.launch(foregroundDispatcher) {
            _charactersListStateFlow.emit(CharactersListState.Loading)

            runCatching {
                withContext(backgroundDispatcher) {
                    interactors.getCharacters(page)
                }
            }.fold(
                onSuccess = { notifyNextCharactersPage(it) },
                onFailure = {
                    charactersPaginationManager.setPaginating(false)
                    _charactersListStateFlow.emit(CharactersListState.Error)
                }
            )
        }
    }

    private suspend fun notifyNextCharactersPage(charactersPage: CharactersPage) {
        with(charactersPaginationManager) {
            update(
                data.copy(
                    isPaginating = false,
                    currentPage = data.nextPage ?: data.currentPage,
                    nextPage = charactersPage.nextPage
                )
            )
        }
        _charactersListStateFlow.emit(CharactersListState.Ready)
        with(_characters) {
            emit(value + charactersPage.characters)
        }
    }
}