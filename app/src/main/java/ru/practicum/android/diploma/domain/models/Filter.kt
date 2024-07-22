package ru.practicum.android.diploma.domain.models

data class Filter(
    val area: Area? = null,
    val industry: Industry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
) {
    fun isEmpty() = (area == null && industry == null && salary == null && !onlyWithSalary)
}
