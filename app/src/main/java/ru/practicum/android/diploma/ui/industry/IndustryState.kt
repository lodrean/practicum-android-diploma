package ru.practicum.android.diploma.ui.industry

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.ErrorType

sealed interface IndustryState {
    data class Content(val industries: List<Industry>) : IndustryState

    data object Loading : IndustryState

    data class Error(val error: ErrorType) : IndustryState

    data class Empty(val message: String) : IndustryState
}