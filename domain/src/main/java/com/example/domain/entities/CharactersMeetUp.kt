package com.example.domain.entities

import java.io.Serializable
import java.util.Date

data class CharactersMeetUp(
    val characters: Pair<Character, Character>,
    val location: Location,
    val episodesTogether: Int,
    val firstMeet: Date,
    val lastMeet: Date
): Serializable