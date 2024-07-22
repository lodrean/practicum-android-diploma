package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailsViewModel


val uiModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(
            vacanciesInteractor = get(),
            filterInteractor = get(),
            application = get(),
        )
    }

    viewModel { (vacancy: Vacancy, vacancyNeedToUpdate: Boolean) ->
        VacancyDetailsViewModel(
            vacancy = vacancy,
            vacancyNeedToUpdate = vacancyNeedToUpdate,
            vacanciesInteractor = get(),
            sharingInteractor = get(),
            favoritesInteractor = get(),
            application = get()
        )
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModel(get())
    }

    viewModel<IndustryViewModel> {
        IndustryViewModel(
            dictionariesInteractor = get(),
            application = get()
        )
    }
}
