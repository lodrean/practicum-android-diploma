package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.AreasRequest
import ru.practicum.android.diploma.data.network.AreasResponse
import ru.practicum.android.diploma.data.network.CountriesRequest
import ru.practicum.android.diploma.data.network.CountriesResponse
import ru.practicum.android.diploma.data.network.VacanciesRequest
import ru.practicum.android.diploma.data.network.VacanciesResponse
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...

        Log.d("DIPLOMA_DEBUG", "key = $accessToken")

        val networkClient = RetrofitNetworkClient(applicationContext)

        lifecycleScope.launch {
            val response = networkClient.doRequest(
                VacanciesRequest(
                    text = "Andriod",
                    page = 1,
                    perPage = 3,
                )
            )

            if (response is VacanciesResponse) {
                for (item in response.items) {
                    Log.d("DIPLOMA_DEBUG", "Vacancies $item")
                }
            }

            val responseAreas = networkClient.doRequest(
                AreasRequest("1")
            )

            if (responseAreas is AreasResponse) {
                Log.d("DIPLOMA_DEBUG", "Area ${responseAreas.area}")
            }

            val responseCountries = networkClient.doRequest(
                CountriesRequest()
            )

            if (responseCountries is CountriesResponse) {
                for (item in responseCountries.countries.slice(0..2)) {
                    Log.d("DIPLOMA_DEBUG", "Countries $item")
                }
            }

        }
    }

}
