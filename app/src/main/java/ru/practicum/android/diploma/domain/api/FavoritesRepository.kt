package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface FavoritesRepository {

    fun getFavoriteVacancies(): Flow<Resource<List<Vacancy>>>

    suspend fun addVacancyToFavorites(vacancy: Vacancy)

    suspend fun deleteVacancyFromFavorites(vacancy: Vacancy)

    suspend fun checkVacancyIsFavorite(vacancy: Vacancy): Boolean

}
