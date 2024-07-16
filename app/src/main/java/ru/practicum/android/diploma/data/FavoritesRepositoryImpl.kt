package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.toEntity
import ru.practicum.android.diploma.util.toVacancy
import java.sql.SQLException

class FavoritesRepositoryImpl(
    private val database: AppDatabase,
    private val context: Context
) : FavoritesRepository {
    override fun getFavoriteVacancies(): Flow<Resource<List<Vacancy>>> = flow {
        try {
            emit(
                Resource.Success(
                    withContext(Dispatchers.IO) {
                        database.favoriteVacancyDao().getFavoriteVacancies().map {
                            it.toVacancy()
                        }
                    }
                )
            )
        } catch (e: SQLException) {
            emit(
                Resource.Error(
                    message = context.getString(R.string.favorites_error)
                )
            )
            throw e
        }
    }

    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        withContext(Dispatchers.IO) {
            database.favoriteVacancyDao().insertVacancy(vacancy.toEntity())
        }
    }

    override suspend fun deleteVacancyFromFavorites(vacancy: Vacancy) {
        withContext(Dispatchers.IO) {
            database.favoriteVacancyDao().deleteFromFavorite(vacancy.toEntity())
        }
    }

    override suspend fun checkVacancyIsFavorite(vacancy: Vacancy): Boolean {
        return withContext(Dispatchers.IO) {
            database.favoriteVacancyDao().findVacancyById(vacancy.id).isNotEmpty()
        }
    }
}
