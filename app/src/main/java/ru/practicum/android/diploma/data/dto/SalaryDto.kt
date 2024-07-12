package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class SalaryDto(
    val currency: String?,
    @SerializedName("from")
    val salaryFrom: Int?,
    val gross: Boolean?,
    @SerializedName("to")
    val salaryTo: Int?
)
