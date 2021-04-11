package com.example.citiboxchallenge.presentation.router

import androidx.navigation.NavHostController
import com.example.citiboxchallenge.presentation.features.characterslist.CharactersListFragmentDirections
import com.example.domain.entities.CharactersMeetUp

class CharactersRouter(private val navController: NavHostController) {

    fun navigateToCharactersMeetUp(charactersMeetUp: CharactersMeetUp) {
        val action = CharactersListFragmentDirections
            .actionCharactersListFragmentToCharacterMeetUpFragment(charactersMeetUp)
        navController.navigate(action)
    }
}