package com.example.citiboxchallenge.presentation.features.characterslist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import arrow.core.right
import com.airbnb.mvrx.test.MvRxTestRule
import com.example.citiboxchallenge.R
import com.example.citiboxchallenge.presentation.router.CharactersRouter
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersMeetUp
import com.example.domain.entities.CharactersPage
import com.example.domain.entities.Location
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import java.util.*

@RunWith(AndroidJUnit4::class)
class CharactersListFragmentTest {

    companion object {
        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }

    private val charactersListInteractorsMock: CharactersListInteractors  = mockk()
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setupTest() {
        startKoin {
            modules(
                module {
                    single {
                        CharactersListViewModel(
                            interactors = charactersListInteractorsMock,
                            charactersPaginationManager = CharactersPaginationManager(),
                            router = CharactersRouter(navController)
                        )
                    }
                }
            )
        }

        coEvery { charactersListInteractorsMock.getCharacters(any()) } returns provideCharactersPageFake().right()
        coEvery { charactersListInteractorsMock.getCharacterMeetUp(any()) } returns provideCharactersMeetUpFake().right()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun provideCharactersFake() = listOf(
        Character(0, "character1", "imageUrl", Location("location1", "locationUrl"), "species1", "type1", emptyList()),
        Character(1, "character2", "imageUrl", Location("location1", "locationUrl"), "species1", "type1", emptyList())
    )

    private fun provideCharactersPageFake() = CharactersPage(1, provideCharactersFake())

    private fun provideCharactersMeetUpFake() = CharactersMeetUp(
        characters = provideCharactersFake().run { first() to last() },
        location = provideCharactersFake().first().location,
        episodesTogether = 1,
        firstMeet = Date(),
        lastMeet = Date()
    )

    @Test
    fun onEnterTheScreenAListOfCharactersIsShown() {
        launchFragmentInContainer<CharactersListFragment>(themeResId = R.style.Theme_AppCompat)
        onCharactersListScreen {
            isCharactersListDisplayed()
            isCharacterAtPositionWithText(0, "character1")
            isCharacterAtPositionWithText(1, "character2")
        }
    }

    @Test
    fun onTapOnCharacterItNavigatesToMeetUp() {
        launchFragmentInContainer<CharactersListFragment>(themeResId = R.style.Theme_AppCompat)
            .onFragment { fragment ->
                navController.setGraph(R.navigation.main_nav_graph)
                navController.setCurrentDestination(R.id.charactersListFragment)
                Navigation.setViewNavController(fragment.requireView(), navController)
            }
        onCharactersListScreen {
            isCharactersListDisplayed()
            tapOnItemAtPosition(0)
        }
        assert(navController.currentDestination?.id == R.id.characterMeetUpFragment)
    }
}