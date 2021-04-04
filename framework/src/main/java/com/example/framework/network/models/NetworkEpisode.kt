package com.example.framework.network.models

import com.example.domain.entities.Episode
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class NetworkEpisode (
    val id: Long,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

fun NetworkEpisode.toDomainEpisode() = Episode(
    id = id.toInt(),
    airDate = SimpleDateFormat("mmmm dd, yyyy", Locale.getDefault()).parse(airDate) ?: Date(),
    characters = characters
)