package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.Filter

sealed interface FilterState {
    data object Empty : FilterState
    data class Filled(
        val filter: Filter,
        val isFilterChanged: Boolean
    ) : FilterState
}
