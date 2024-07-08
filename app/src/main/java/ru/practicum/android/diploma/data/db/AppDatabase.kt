package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyPhoneDao
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyPhoneEntity

@Database(
    version = 1,
    entities = [
        FavoriteVacancyEntity::class,
        FavoriteVacancyPhoneEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao

    abstract fun favoriteVacancyPhoneDao(): FavoriteVacancyPhoneDao
}
