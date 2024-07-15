package ru.practicum.android.diploma.presentation.favorites

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoritesState {

    data object Loading : FavoritesState

    data class Content(
        val vacanciesList: List<Vacancy>
    ) : FavoritesState

    data object Error : FavoritesState

    data object Empty : FavoritesState

}
