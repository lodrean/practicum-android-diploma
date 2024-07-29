package ru.practicum.android.diploma.presentation.region

import ru.practicum.android.diploma.domain.models.Area

sealed interface RegionState {

    data class Content(
        val regionsList: List<Area>
    ) : RegionState

    data object Error : RegionState

    data object Empty : RegionState

    data object NoRegion : RegionState
}
