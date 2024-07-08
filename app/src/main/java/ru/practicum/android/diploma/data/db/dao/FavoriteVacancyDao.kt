package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVacancy(vacancy: FavoriteVacancyEntity)

    @Query("select * from favorite_vacancies_table")
    fun getFavoriteVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Delete
    fun deleteFromFavorite(vacancy: FavoriteVacancyEntity)
}
