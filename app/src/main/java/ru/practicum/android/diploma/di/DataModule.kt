package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module

val dataModule = module {

    single {
        Gson()
    }

}
