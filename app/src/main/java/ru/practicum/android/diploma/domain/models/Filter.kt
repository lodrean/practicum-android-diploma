package ru.practicum.android.diploma.domain.models

data class Filter(
    val text: String = "",
    val area: Area? = null,
    val industry: Industry? = null,
    val onlyWithSalary: Boolean = false,
)
