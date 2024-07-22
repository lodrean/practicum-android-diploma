package ru.practicum.android.diploma.domain.models

data class Filter(
    val area: Area? = null,
    val industry: Industry? = null,
    val onlyWithSalary: Boolean = false,
)
