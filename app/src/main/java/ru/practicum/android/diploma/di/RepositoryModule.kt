package ru.practicum.android.diploma.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import ru.practicum.android.diploma.data.DictionariesRepositoryImpl
import ru.practicum.android.diploma.data.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.FilterRepository
import ru.practicum.android.diploma.data.FilterRepostoryImpl
import ru.practicum.android.diploma.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.sharing.SharingRepository
import ru.practicum.android.diploma.data.sharing.SharingRepositoryImpl
import ru.practicum.android.diploma.domain.api.DictionariesRepository
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.api.VacanciesRepository

val repositoryModule = module {

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(
            context = get(),
            networkClient = get(),
        )
    }

    single<DictionariesRepository> {
        DictionariesRepositoryImpl(
            context = get(),
            networkClient = get(),
        )
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(
            database = get(),
            defaultDispatcher = Dispatchers.IO,
        )
    }

    single<SharingRepository> {
        SharingRepositoryImpl(get(), get())
    }

    single<FilterRepository> {
        FilterRepostoryImpl(
            context = get(),
        )
    }
}
