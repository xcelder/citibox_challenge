package com.example.citiboxchallenge.presentation.features.charactermeetup

import com.example.citiboxchallenge.R
import com.example.citiboxchallenge.utils.isDisplayed
import com.example.citiboxchallenge.utils.isVisible
import com.example.citiboxchallenge.utils.onViewWithText

class onCharacterMeetUpScreen(block: onCharacterMeetUpScreen.() -> Unit) {
    init { block() }

    fun checkIfAvatarsAreVisible() {
        R.id.firstCharacterAvatar.isVisible()
        R.id.secondCharacterAvatar.isVisible()
    }

    fun checkCharacterNameIsVisible(name: String) =
        onViewWithText(name).isDisplayed()

    fun checkLocationIsVisible(location: String) =
        onViewWithText(location).isDisplayed()

    fun checkEpisodesIsVisible(episodes: String) =
        onViewWithText(episodes).isDisplayed()
}