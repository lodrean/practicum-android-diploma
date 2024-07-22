package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.country.CountryViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.region.RegionViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.workplace.WorkplaceViewModel

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

    viewModel<WorkplaceViewModel> {
        WorkplaceViewModel(get(), get())
    }

    viewModel<CountryViewModel> {
        CountryViewModel(get(), get())
    }

    viewModel<RegionViewModel> {
        RegionViewModel(get(), get())

    viewModel<IndustryViewModel> {
        IndustryViewModel(
            dictionariesInteractor = get(),
            application = get()
        )
    }
}
