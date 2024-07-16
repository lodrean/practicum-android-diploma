package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVacancy(vacancy: FavoriteVacancyEntity)

    @Query("select * from favorite_vacancies_table")
    fun getFavoriteVacancies(): List<FavoriteVacancyEntity>

    @Delete
    fun deleteFromFavorite(vacancy: FavoriteVacancyEntity)

    @Query("select vacancy_id from favorite_vacancies_table where vacancy_id = :vacancyId")
    fun findVacancyById(vacancyId: String): List<String>
}
