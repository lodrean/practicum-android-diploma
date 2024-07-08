package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyPhoneEntity

@Dao
interface FavoriteVacancyPhoneDao {
    @Query("select * from favorite_vacancy_phone_table where vacancy_id = :vacancyId")
    fun getPhones(vacancyId: Int): Flow<List<FavoriteVacancyPhoneEntity>>

    @Insert
    fun insertPhone(favoriteVacancyPhoneEntity: FavoriteVacancyPhoneEntity)
}
