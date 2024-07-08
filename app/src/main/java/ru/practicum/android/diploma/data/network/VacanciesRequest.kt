package ru.practicum.android.diploma.data.network

import com.google.gson.annotations.SerializedName

data class VacanciesRequest(
    val text: String,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
)
