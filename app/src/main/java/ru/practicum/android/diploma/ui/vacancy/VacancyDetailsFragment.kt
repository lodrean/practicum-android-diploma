package ru.practicum.android.diploma.ui.vacancy

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.UtilityFunctions
import kotlin.math.roundToInt

class VacancyDetailsFragment : Fragment() {
    private val vacancyDetailsViewModel: VacancyDetailsViewModel by viewModel<VacancyDetailsViewModel>() {
        parametersOf(requireArguments().getSerializable(Vacancy.EXTRAS_KEY))
    }
    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding
        get() = _binding!!

    private fun render(result: Result<Vacancy>) {
        if (result.isSuccess) {
            val vacancy = result.getOrNull()
            if (vacancy != null) {
                binding.vacancyNameTextView.text = vacancy.name
                binding.employerNameTextView.text = vacancy.employerName
                binding.employerCity.text = vacancy.employerCity
                binding.experienceTextView.text = vacancy.experienceName
                binding.employmentScheduleTextView.text =
                    "%s, %s".format(vacancy.employment, vacancy.schedule)
                binding.vacancyDescriptionTextView.text = Html.fromHtml(vacancy.description)

                binding.keySkillsTitle.isVisible = vacancy.keySkills != ""
                binding.keySkills.isVisible = vacancy.keySkills != ""
                binding.keySkills.text = vacancy.keySkills
                binding.vacancySalaryTextView.text = UtilityFunctions.formatSalary(vacancy, requireContext())
                Glide.with(binding.root).load(vacancy.employerLogoPath)
                    .apply(RequestOptions().placeholder(R.drawable.placeholder))
                    .transform(
                        CenterCrop(),
                        RoundedCorners(
                            resources.getDimension(R.dimen.size_l).roundToInt()
                        )
                    ).into(binding.employerLogoImageView)
            } else {
                return
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)

        vacancyDetailsViewModel.observeState().observe(requireActivity()) {
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
}
