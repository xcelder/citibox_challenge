package com.example.citiboxchallenge.presentation.features.characterslist.model

data class CharactersPaginationData(
    val isPaginating: Boolean = false,
    var currentPage: Int = 0,
    var nextPage: Int? = 1
)