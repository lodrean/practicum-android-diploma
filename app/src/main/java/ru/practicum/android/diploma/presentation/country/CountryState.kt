package ru.practicum.android.diploma.presentation.country

import ru.practicum.android.diploma.domain.models.Area

sealed interface CountryState {

    data class Content(
        val countriesList: List<Area>
    ) : CountryState

    data object Error : CountryState

    data object Empty : CountryState
}
