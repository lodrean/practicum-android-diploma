package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.CountryDto

data class CountriesResponse(
    val countries: List<CountryDto>
) : Response()
