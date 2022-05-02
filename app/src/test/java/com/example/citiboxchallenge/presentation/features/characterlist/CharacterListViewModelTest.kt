package com.example.citiboxchallenge.presentation.features.characterlist

import arrow.core.left
import arrow.core.right
import com.airbnb.mvrx.test.MvRxTestRule
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListInteractors
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListState
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListViewModel
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersPaginationManager
import com.example.citiboxchallenge.presentation.features.characterslist.model.CharactersPaginationData
import com.example.citiboxchallenge.presentation.router.CharactersRouter
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.entities.Location
import com.example.domain.error.NetworkError
import com.example.usecase.GetCharacters
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.ClassRule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    companion object {
        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }

    private val testDispatcher = TestCoroutineDispatcher()

    private val interactors: CharactersListInteractors = mockk()

    private val charactersPaginationManager: CharactersPaginationManager = mockk(relaxed = true)

    private val router: CharactersRouter = mockk()

    private val charactersListViewModel = CharactersListViewModel(
        interactors,
        charactersPaginationManager,
        router
    )

    @Test
    fun `when loadNextCharactersPage with nextPage nonnull then updates pagination data and emit characters`() {
        testDispatcher.runBlockingTest {
            // Given
            val expectedData = CharactersPaginationData()
            val expectedPage = 0
            val expectedCharactersPage = CharactersPage(
                nextPage = expectedPage + 1,
                characters = listOf(
                    Character(
                        id = 1,
                        name = "name",
                        location = Location("location", "url"),
                        episode = listOf(1),
                        species = "species",
                        type = "type",
                        imageUrl = "image url"
                    )
                )
            )

            val getCharactersUseCaseMockk = mockk<GetCharacters>()

            every { charactersPaginationManager.data } returns expectedData
            every { interactors.getCharacters } returns getCharactersUseCaseMockk
            coEvery { getCharactersUseCaseMockk.invoke(any()) } returns expectedCharactersPage.right()

            val stateUpdates = mutableListOf<CharactersListState>()

            val stateJob = launch { charactersListViewModel.stateFlow.toList(stateUpdates) }

            // When
            charactersListViewModel.loadNextCharactersPage()

            // Then
            coVerify { getCharactersUseCaseMockk.invoke(any()) }

            verify {
                charactersPaginationManager.update(
                    match {
                        it.currentPage == expectedData.nextPage &&
                                it.nextPage == expectedCharactersPage.nextPage
                    })
            }

            stateUpdates.forEachIndexed { index, state ->
                when (index) {
                    1 -> assertEquals(CharactersListState(isLoading = true), state)
                    2 -> assertEquals(CharactersListState(characters = expectedCharactersPage.characters), state)
                }
            }

            stateJob.cancel()
        }
    }

    @Test
    fun `when loadNextCharactersPage nextPage null then not updates pagination data and emit nothing`() {
        testDispatcher.runBlockingTest {
            // Given
            val expectedData = CharactersPaginationData(nextPage = null)
            val expectedPage = 0
            val expectedCharactersPage = CharactersPage(
                nextPage = expectedPage + 1,
                characters = listOf(
                    Character(
                        id = 1,
                        name = "name",
                        location = Location("location", "url"),
                        episode = listOf(1),
                        species = "species",
                        type = "type",
                        imageUrl = "image url"
                    )
                )
            )

            val getCharactersUseCaseMockk = mockk<GetCharacters>()

            every { charactersPaginationManager.data } returns expectedData
            every { interactors.getCharacters } returns getCharactersUseCaseMockk
            coEvery { getCharactersUseCaseMockk.invoke(any()) } returns expectedCharactersPage.right()

            val stateUpdates = mutableListOf<CharactersListState>()

            val stateJob = launch { charactersListViewModel.stateFlow.toList(stateUpdates) }

            // When
            charactersListViewModel.loadNextCharactersPage()

            // Then
            verify { getCharactersUseCaseMockk wasNot Called }

            verify(exactly = 0) { charactersPaginationManager.update(any()) }

            assertEquals(1, stateUpdates.count())

            stateJob.cancel()
        }
    }

    @Test
    fun `when loadNextCharactersPage with error then emits error and not updates pagination data or characters`() {
        testDispatcher.runBlockingTest {
            // Given
            val expectedData = CharactersPaginationData()
            val getCharactersUseCaseMockk = mockk<GetCharacters>()

            every { charactersPaginationManager.data } returns expectedData
            every { interactors.getCharacters } returns getCharactersUseCaseMockk
            coEvery { getCharactersUseCaseMockk.invoke(any()) } returns NetworkError.left()

            val stateUpdates = mutableListOf<CharactersListState>()

            val stateJob = launch { charactersListViewModel.stateFlow.toList(stateUpdates) }

            // When
            charactersListViewModel.loadNextCharactersPage()

            // Then
            coVerify { getCharactersUseCaseMockk.invoke(any()) }

            verify(exactly = 0) { charactersPaginationManager.update(any()) }

            stateUpdates.forEachIndexed { index, state ->
                when (index) {
                    1 -> assertEquals(CharactersListState(isLoading = true), state)
                    2 -> assertEquals(CharactersListState(isError = true), state)
                }
            }

            stateJob.cancel()
        }
    }
}