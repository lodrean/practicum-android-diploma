package ru.practicum.android.diploma.data.network

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.VacancyDto

data class VacanciesResponse(
    @SerializedName("alternate_url")
    val alternateUrl: String,
    // val arguments: Any,
    // val clusters: Any,
    val fixes: Any,
    val found: Int,
    val items: List<VacancyDto>,
    val page: Int,
    val pages: Int,
    @SerializedName("per_page")
    val perPage: Int,
    // val suggests: Any
) : Response()

