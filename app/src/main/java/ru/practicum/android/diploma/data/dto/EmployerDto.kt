package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    @SerializedName("accredited_it_employer")
    val accreditedItEmployer: Boolean,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto,
    val name: String,
    val trusted: Boolean,
    val url: String,
    @SerializedName("vacancies_url")
    val vacanciesUrl: String
)
