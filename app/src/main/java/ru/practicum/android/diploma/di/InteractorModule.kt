package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImlp
import ru.practicum.android.diploma.domain.impl.VacanciesInteractorImpl

val interactorModule = module {

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(
            repository = get(),
        )
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImlp(get())
    }
}
