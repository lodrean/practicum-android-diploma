package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreaDto

data class AreasByIdResponse(
    val area: AreaDto,
) : Response()
