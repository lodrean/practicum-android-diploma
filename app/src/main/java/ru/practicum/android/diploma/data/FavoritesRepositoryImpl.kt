package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.toVacancy
import java.sql.SQLException

class FavoritesRepositoryImpl(
    private val database: AppDatabase,
    private val context: Context
) : FavoritesRepository {
    override fun updateFavorites(): Flow<Resource<List<Vacancy>>> = flow {

        try {
            emit(
                Resource.Success(
                    database.favoriteVacancyDao().getFavoriteVacancies().map {
                        it.toVacancy()
                    }
                )
            )
        } catch (e: SQLException) {
            emit(
                Resource.Error(
                    message = context.getString(R.string.favorites_error)
                )
            )
        }
    }
}
