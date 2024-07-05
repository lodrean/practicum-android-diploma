package ru.practicum.android.diploma.di

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SHARED_PREFERENCES_KEY = "sharedPref"

val dataModule = module {

    single {
        Gson()
    }

    single {
        androidContext()
            .getSharedPreferences(SHARED_PREFERENCES_KEY, AppCompatActivity.MODE_PRIVATE)
    }

}
