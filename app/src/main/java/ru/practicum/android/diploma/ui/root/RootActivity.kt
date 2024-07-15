package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.util.Resource

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

        // Пример использования интерактора вакансий
        testApi()
    }

    private fun changeBottomNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigationView.isVisible = isVisible
        binding.separator.isVisible = isVisible
    }

    private fun testApi() {
        val interactor: VacanciesInteractor = getKoin().get()

        val debugTag = "DIPLOMA_DEBUG"

        lifecycleScope.launch {
            // Ищем вакансии
            interactor.searchVacancies("Android", 0, 2).collect { resource ->
                if (resource is Resource.Error) {
                    Log.d(debugTag, "Search error: ${resource.message}")
                } else {
                    resource.data?.vacancies?.forEach { vacancy ->
                        Log.d(debugTag, "Item: $vacancy")

                        // Получаем расширенную вакансию (с описанием)
                        interactor.updateToFullVacancy(vacancy).collect { resourceFull ->
                            if (resourceFull is Resource.Error) {
                                Log.d(debugTag, "Get full error: ${resourceFull.message}")
                            } else {
                                Log.d(debugTag, "Full item: ${resourceFull.data}")
                            }
                        }
                    }

                }
            }
        }
    }

}
