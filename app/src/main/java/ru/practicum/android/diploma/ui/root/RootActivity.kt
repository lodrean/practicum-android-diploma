package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.data.network.AreasRequest
import ru.practicum.android.diploma.data.network.AreasResponse
import ru.practicum.android.diploma.data.network.CountriesRequest
import ru.practicum.android.diploma.data.network.CountriesResponse
import ru.practicum.android.diploma.data.network.VacanciesRequest
import ru.practicum.android.diploma.data.network.VacanciesResponse
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.VacancyRequest
import ru.practicum.android.diploma.data.network.VacancyResponse

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search_fragment -> {
                    changeBottomNavigationVisibility(true)
                }

                R.id.favorites_fragment -> {
                    changeBottomNavigationVisibility(true)
                }

                R.id.crew_fragment -> {
                    changeBottomNavigationVisibility(true)
                }

                else -> {
                    changeBottomNavigationVisibility(false)
                }
            }
        }

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

                if (response.items.isNotEmpty()) {
                    val vacancyFullResponse = networkClient.doRequest(
                        VacancyRequest(response.items.first().id)
                    )

                    if (vacancyFullResponse is VacancyResponse) {
                        Log.d("DIPLOMA_DEBUG", "Vacancy full ${vacancyFullResponse.vacancy}")
                    }
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

    private fun changeBottomNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigationView.isVisible = isVisible
        binding.separator.isVisible = isVisible
    }

}
