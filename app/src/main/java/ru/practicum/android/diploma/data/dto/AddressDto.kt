package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    val building: String,
    val city: String,
    val description: Any,
    val id: String,
    val lat: Double,
    val lng: Double,
    val metro: MetroDto,
    @SerializedName("metro_stations")
    val metroStations: List<MetroStationDto>,
    val raw: String,
    val street: String
)
