package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyFullDto

data class VacancyResponse(
    val vacancy: VacancyFullDto,
) : Response()
