package ru.practicum.android.diploma.ui

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class ListItemUiModel {

    data class VacancyListItem(val vacancy: Vacancy) :
        ListItemUiModel()
    data object Loading:
        ListItemUiModel()
    data object Empty:
        ListItemUiModel()
}
