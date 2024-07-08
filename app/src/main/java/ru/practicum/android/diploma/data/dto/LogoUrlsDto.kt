package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("240")
    val variant240: String,
    @SerializedName("90")
    val variant90: String,
    val original: String
)
