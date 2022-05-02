package com.example.citiboxchallenge.presentation.features.charactermeetup

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.citiboxchallenge.R
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersMeetUp
import com.example.domain.entities.Location
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class CharactersMeetUpFragmentTest {

    private fun provideCharactersFake() = listOf(
        Character(0, "character1", "imageUrl", Location("location1", "locationUrl"), "species1", "type1", emptyList()),
        Character(1, "character2", "imageUrl", Location("location1", "locationUrl"), "species1", "type1", emptyList())
    )

    private fun provideCharactersMeetUpFake() = CharactersMeetUp(
        characters = provideCharactersFake().run { first() to last() },
        location = provideCharactersFake().first().location,
        episodesTogether = 1,
        firstMeet = Date(),
        lastMeet = Date()
    )

    @Test
    fun OnEnterTheScreenMeetUpDataIsShown() {
        launchFragmentInContainer<CharacterMeetUpFragment>(
            themeResId = R.style.Theme_AppCompat,
            fragmentArgs = CharacterMeetUpFragmentArgs(provideCharactersMeetUpFake()).toBundle()
        )

        onCharacterMeetUpScreen {
            checkIfAvatarsAreVisible()
            checkCharacterNameIsVisible("character1")
            checkCharacterNameIsVisible("character2")
            checkLocationIsVisible("location1")
            checkEpisodesIsVisible("1")
        }
    }
}