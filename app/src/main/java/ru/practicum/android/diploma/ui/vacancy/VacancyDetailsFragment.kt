package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.UtilityFunctions

class VacancyDetailsFragment : Fragment() {
    private val vacancyDetailsViewModel: VacancyDetailsViewModel by viewModel<VacancyDetailsViewModel> {
        parametersOf(requireArguments().getSerializable(Vacancy.EXTRAS_KEY))
    }
    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding
        get() = _binding!!

    private fun render(state: VacancyDetailsState) {
        when (state) {
            is VacancyDetailsState.Content -> {
                loadContentData(state.vacancy)
            }

            is VacancyDetailsState.VacancyNotFoundedError -> {
                binding.vacancyErrorTextView.text =
                    getString(R.string.vacancy_not_found_or_deleted)
                Glide.with(binding.root).load(R.drawable.no_vacancy_data)
                    .into(binding.vacancyErrorImageView)
                showError()
            }

            is VacancyDetailsState.VacancyServerError -> {
                binding.vacancyErrorTextView.text = getString(R.string.server_error)
                Glide.with(binding.root).load(R.drawable.server_error)
                    .into(binding.vacancyErrorImageView)
                showError()
            }

            is VacancyDetailsState.Loading -> {
                binding.contentScrollView.isVisible = false
                binding.layoutError.isVisible = false
                binding.vacancyDetailsProgressBar.isVisible = true
            }
        }
    }

    private fun showError() {
        binding.contentScrollView.isVisible = false
        binding.vacancyDetailsProgressBar.isVisible = false
        binding.layoutError.isVisible = true
    }

    private fun loadContentData(vacancy: Vacancy) {
        binding.vacancyNameTextView.text = vacancy.name
        binding.employerNameTextView.text = vacancy.employerName
        binding.employerCity.text = vacancy.employerCity
        binding.experienceTextView.text = vacancy.experienceName
        binding.employmentScheduleTextView.text =
            "%s, %s".format(vacancy.employment, vacancy.schedule)
        binding.vacancyDescriptionTextView.text =
            Html.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.keySkillsTitle.isVisible = vacancy.keySkills?.isNotEmpty() ?: false
        binding.keySkills.isVisible = vacancy.keySkills?.isNotEmpty() ?: false
        if (vacancy.keySkills != null) {
            binding.keySkills.text = vacancy.keySkills.split(",").toList().map {
                "• %s".format(it.replace("\"", "").replace(",", ""))
            }.joinToString(separator = "\n")
        }

        binding.vacancySalaryTextView.text = UtilityFunctions.formatSalary(vacancy, requireContext())
        Glide.with(binding.root).load(vacancy.employerLogoPath).placeholder(R.drawable.placeholder)
            .centerInside()
            .transform(RoundedCorners(binding.root.resources.getDimensionPixelSize(R.dimen.size_l)))
            .into(binding.employerLogoImageView)
        binding.vacancyDetailsProgressBar.isVisible = false
        binding.layoutError.isVisible = false
        binding.contentScrollView.isVisible = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)

        vacancyDetailsViewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArgs(vacancy: Vacancy): Bundle = bundleOf(Vacancy.EXTRAS_KEY to vacancy)
    }
}
