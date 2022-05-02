package com.example.citiboxchallenge.presentation.features.characterslist

import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.citiboxchallenge.R
import com.example.citiboxchallenge.utils.isDisplayed
import com.example.citiboxchallenge.utils.isVisible
import com.example.citiboxchallenge.utils.onItemAtPosition
import com.example.citiboxchallenge.utils.tapOnItemAtPosition


class onCharactersListScreen(block: onCharactersListScreen.() -> Unit) {

    init { block() }

    fun isCharactersListDisplayed() = R.id.charactersView.isVisible()

    fun isCharacterAtPositionWithText(position: Int, text: String) =
        onItemAtPosition(position, hasDescendant(withText(text)))
        .isDisplayed()

    fun tapOnItemAtPosition(position: Int) = R.id.charactersView.tapOnItemAtPosition(position)

}
