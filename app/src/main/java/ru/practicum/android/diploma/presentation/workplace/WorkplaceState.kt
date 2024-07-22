package ru.practicum.android.diploma.presentation.workplace

import ru.practicum.android.diploma.domain.models.Area

sealed interface WorkplaceState {

    data object NothingIsPicked : WorkplaceState

    data class CountryIsPicked(val country: Area) : WorkplaceState

    data class CountryAndRegionIsPicked(val country: Area, val city: Area) : WorkplaceState
}
