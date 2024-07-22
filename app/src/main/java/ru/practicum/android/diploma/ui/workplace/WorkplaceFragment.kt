package ru.practicum.android.diploma.ui.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.presentation.workplace.WorkplaceState
import ru.practicum.android.diploma.presentation.workplace.WorkplaceViewModel

class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    private val binding
        get() = _binding!!

    val workplaceViewModel by viewModel<WorkplaceViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        workplaceViewModel.loadFilter()

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.countryBar.setOnClickListener {
            findNavController().navigate(R.id.action_workplace_fragment_to_country_fragment)
        }

        binding.regionBar.setOnClickListener {
            findNavController().navigate(R.id.action_workplace_fragment_to_region_fragment)
        }

        binding.actionIconCountry.setOnClickListener {
            workplaceViewModel.deleteCountry()
        }

        binding.actionIconRegion.setOnClickListener {
            workplaceViewModel.deleteRegion()
        }

        binding.chooseButton.setOnClickListener {
            findNavController().navigateUp()
        }

        workplaceViewModel.getWorkplaceStateLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is WorkplaceState.CountryIsPicked -> {
                    showCountry(it.country.name)
                    binding.chooseButton.isVisible = true
                }

                is WorkplaceState.CountryAndRegionIsPicked -> {
                    showCountry(it.country.name)
                    showRegion(it.city.name)
                    binding.chooseButton.isVisible = true
                }

                is WorkplaceState.NothingIsPicked -> {
                    hideCountry()
                    hideRegion()
                    binding.chooseButton.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showCountry(country: String) {
        binding.countryHint.isVisible = false
        binding.countryName.isVisible = true
        binding.countryTitle.isVisible = true

        binding.actionIconCountry.setImageResource(R.drawable.close_icon)
        binding.actionIconCountry.isEnabled = true

        binding.countryName.text = country
    }

    private fun hideCountry() {
        binding.countryHint.isVisible = true
        binding.countryName.isVisible = false
        binding.countryTitle.isVisible = false

        binding.actionIconCountry.setImageResource(R.drawable.arrow_forward)
        binding.actionIconCountry.isEnabled = false
    }

    private fun showRegion(region: String) {
        binding.regionHint.isVisible = false
        binding.regionName.isVisible = true
        binding.regionTitle.isVisible = true

        binding.actionIconRegion.setImageResource(R.drawable.close_icon)
        binding.actionIconRegion.isEnabled = true

        binding.regionName.text = region
    }

    private fun hideRegion() {
        binding.regionHint.isVisible = true
        binding.regionName.isVisible = false
        binding.regionTitle.isVisible = false

        binding.actionIconRegion.setImageResource(R.drawable.arrow_forward)
        binding.actionIconRegion.isEnabled = false
    }
}
