package ru.practicum.android.diploma.ui.vacancy

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.VacancyDetailsViewModel

class VacancyDetailsFragment(val vacancy: Vacancy) : Fragment() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val vacancyDetailsViewModel = getViewModel<VacancyDetailsViewModel> {
        parametersOf(
            requireArguments().getParcelable(
                "", Vacancy::class.java
            )
        )
    }

    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding
        get() = _binding!!

    private fun render(vacancy: Vacancy) {

        binding.vacancyNameTextView.text = vacancy.name
        binding.vacancySalaryTextView.text = "%s%s%s".format(
            if (vacancy.salaryFrom != null) getString(R.string.from).format(vacancy.salaryFrom) else "",
            if (vacancy.salaryTo != null) getString(R.string.to).format(vacancy.salaryTo) else "",
            vacancy.salaryCurrencyName
        )
        binding.experienceTextView.text = vacancy.experienceName
        binding.employmentScheduleTextView.text = "%s, %s".format(vacancy.schedule, vacancy.schedule)
        binding.employerNameTextView.text = vacancy.employerName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)

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
