package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorites.FavoritesState
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.ui.adapters.VacancyAdapter
import ru.practicum.android.diploma.ui.search.SearchFragment.Companion.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsFragment
import ru.practicum.android.diploma.util.debounce

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModel<FavoritesViewModel>()
    private var adapter: VacancyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStateLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is FavoritesState.Content -> showContent(it.vacanciesList)
                is FavoritesState.Empty -> showEmptyMessage()
                is FavoritesState.Error -> showError()
                is FavoritesState.Loading -> showLoading()
            }
        }

        val onVacancyClickDebounce = debounce<Vacancy>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            launchVacancyDetails(vacancy)
        }
        adapter = VacancyAdapter { onVacancyClickDebounce(it) }
        binding.favoritesList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFavoritesList()
    }

    private fun showContent(listOfVacancies: List<Vacancy>) {
        adapter?.setData(listOfVacancies)
        binding.errorImage.isVisible = false
        binding.errorText.isVisible = false
        binding.loading.isVisible = false
        binding.favoritesList.isVisible = true
    }

    private fun showEmptyMessage() {
        binding.errorImage.setImageResource(R.drawable.favorites_empty_list_image)
        binding.errorText.setText(R.string.empty_favorites_list)

        binding.errorImage.isVisible = true
        binding.errorText.isVisible = true
        binding.loading.isVisible = false
        binding.favoritesList.isVisible = false
    }

    private fun showError() {
        binding.errorImage.setImageResource(R.drawable.empty_list_image)
        binding.errorText.setText(R.string.favorites_error)

        binding.errorImage.isVisible = true
        binding.errorText.isVisible = true
        binding.loading.isVisible = false
        binding.favoritesList.isVisible = false
    }

    private fun showLoading() {
        binding.errorImage.isVisible = false
        binding.errorText.isVisible = false
        binding.loading.isVisible = true
        binding.favoritesList.isVisible = false
    }

    private fun launchVacancyDetails(vacancy: Vacancy) {
        findNavController().navigate(
            R.id.action_favorites_fragment_to_vacancy_details_fragment,
            VacancyDetailsFragment.createArgs(vacancy = vacancy, vacancyNeedUpdate = false)
        )
    }
}
