package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.Filter

sealed interface FilterState {
    data object Default : FilterState

    data class Filtered(
        val filter: Filter
    ) : FilterState

    data class readyToSave(
        val showButton: Boolean
    ) : FilterState
}
