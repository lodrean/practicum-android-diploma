package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Area

fun interface OnAreaItemClickListener {
    fun onClick(area: Area)
}
