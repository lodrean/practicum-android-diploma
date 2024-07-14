package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacancyListItemUiModel {

    data object Empty : VacancyListItemUiModel()

    data class VacancyItem(val vacancy: Vacancy) : VacancyListItemUiModel()

    data object Loading : VacancyListItemUiModel()
}
