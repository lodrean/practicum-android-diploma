package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.search.SearchViewModel

val uiModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(get(), androidApplication())
    }
}
