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
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.VacancyDetailsViewModel
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale
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
            binding.vacancyNameTextView.text = result.getOrNull()?.name
            binding.employerNameTextView.text = result.getOrNull()?.employerName
            binding.employerCity.text = result.getOrNull()?.employerCity
            binding.experienceTextView.text = result.getOrNull()?.experienceName
            binding.employmentScheduleTextView.text =
                "%s, %s".format(result.getOrNull()?.employment, result.getOrNull()?.schedule)
            binding.vacancyDescriptionTextView.text = Html.fromHtml(result.getOrNull()?.description)

            binding.keySkillsTitle.isVisible = result.getOrNull()?.keySkills != ""
            binding.keySkills.isVisible = result.getOrNull()?.keySkills != ""
            binding.keySkills.text = result.getOrNull()?.keySkills

            Glide.with(binding.root).load(result.getOrNull()?.employerLogoPath)
                .apply(RequestOptions().placeholder(R.drawable.placeholder))
                .transform(
                    CenterCrop(),
                    RoundedCorners(
                        resources.getDimension(R.dimen.size_l).roundToInt()
                    )
                ).into(binding.employerLogoImageView)
        }
        if (result.isFailure) {

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
