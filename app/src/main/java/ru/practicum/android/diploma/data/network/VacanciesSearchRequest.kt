package ru.practicum.android.diploma.data.network

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.Filter

data class VacanciesSearchRequest(
    val text: String,
    val filter: Filter,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
)
