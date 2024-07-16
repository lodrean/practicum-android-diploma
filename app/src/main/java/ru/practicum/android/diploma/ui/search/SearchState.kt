package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchState {

    data object NextPageLoading : SearchState
    data object Loading : SearchState
    data object Default : SearchState

    data class Content(
        val vacanciesList: List<Vacancy>,
        val countOfVacancies: Int?
    ) : SearchState

    data class Error(
        val errorMessage: String
    ) : SearchState

    data class NoInternet(
        val errorMessage: String
    ) : SearchState

    data class Empty(
        val message: String
    ) : SearchState

}
