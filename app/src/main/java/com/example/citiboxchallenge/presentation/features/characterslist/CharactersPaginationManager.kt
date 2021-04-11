package com.example.citiboxchallenge.presentation.features.characterslist

import com.example.citiboxchallenge.presentation.features.characterslist.model.CharactersPaginationData

class CharactersPaginationManager {

    var data = CharactersPaginationData()
        private set

    fun update(data: CharactersPaginationData) {
        this.data = data
    }
}