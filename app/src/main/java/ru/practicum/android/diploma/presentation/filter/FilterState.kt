package ru.practicum.android.diploma.presentation.filter

sealed interface FilterState {
    data object Default : FilterState

    data class Filtered(
        val area: String,
        val industry: String,
        val salary: String,
        val isSalaryRequired: Boolean
    ) : FilterState
    data object readyToSave : FilterState
}
