package com.example.framework.db.entities

import androidx.room.ColumnInfo
import com.example.domain.entities.Location

internal data class DbLocation(
    @ColumnInfo(name = "location_name") val name: String,
    @ColumnInfo(name = "location_url") val url: String
)

internal fun DbLocation.toDomainLocation() = Location(name, url)

internal fun dbLocationFromDomainLocation(location: Location) = with(location) {
    DbLocation(name, url)
}