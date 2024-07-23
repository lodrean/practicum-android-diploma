package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.UtilityFunctions

class VacancyDetailsFragment : Fragment() {
    private val vacancyDetailsViewModel: VacancyDetailsViewModel by viewModel<VacancyDetailsViewModel> {
        parametersOf(
            requireArguments().getSerializable(Vacancy.EXTRAS_KEY),
            requireArguments().getBoolean(VACANCY_NEED_TO_UPDATE_KEY)
        )
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
                binding.vacancyErrorImageView.setImageResource(R.drawable.no_vacancy_data)
                showError()
            }

            is VacancyDetailsState.VacancyServerError -> {
                binding.vacancyErrorTextView.text = getString(R.string.server_error)
                binding.vacancyErrorImageView.setImageResource(R.drawable.server_error)
                showError()
            }

            is VacancyDetailsState.Loading -> {
                binding.vacancyErrorImageView.isVisible = false
                binding.contentScrollView.isVisible = false
                binding.layoutError.isVisible = false
                binding.vacancyDetailsProgressBar.isVisible = true
            }
        }
    }

    private fun showError() {
        binding.vacancyErrorImageView.isVisible = true
        binding.contentScrollView.isVisible = false
        binding.vacancyDetailsProgressBar.isVisible = false
        binding.layoutError.isVisible = true
        binding.topAppBar.menu.findItem(R.id.favorite).setEnabled(false)
        binding.topAppBar.menu.findItem(R.id.share).setEnabled(false)
    }

    private fun loadContentData(vacancy: Vacancy) {
        binding.vacancyNameTextView.text = vacancy.name
        binding.employerNameTextView.text = vacancy.employerName
        binding.employerCity.text = vacancy.employerCity
        binding.experienceTextView.text = vacancy.experienceName
        binding.employmentScheduleTextView.text =
            getString(R.string.employment_schedule).format(vacancy.employment, vacancy.schedule)
        binding.vacancyDescriptionTextView.text =
            Html.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        fillKeySkills(vacancy)
        fillContacts(vacancy)

        binding.vacancySalaryTextView.text =
            UtilityFunctions.formatSalary(vacancy, requireContext())
        Glide.with(binding.root).load(vacancy.employerLogoPath).placeholder(R.drawable.placeholder)
            .centerInside()
            .transform(RoundedCorners(binding.root.resources.getDimensionPixelSize(R.dimen.size_l)))
            .into(binding.employerLogoImageView)
        binding.vacancyErrorImageView.isVisible = false
        binding.vacancyDetailsProgressBar.isVisible = false
        binding.layoutError.isVisible = false
        binding.contentScrollView.isVisible = true
        binding.topAppBar.menu.findItem(R.id.favorite).setEnabled(true)
        binding.topAppBar.menu.findItem(R.id.share).setEnabled(true)
        if (vacancy.isFavorite) {
            binding.topAppBar.menu.findItem(R.id.favorite).setIcon(R.drawable.favorites_on_icon)
        } else {
            binding.topAppBar.menu.findItem(R.id.favorite).setIcon(R.drawable.favorites_off_icon)
        }
    }

    private fun fillKeySkills(vacancy: Vacancy) {
        binding.keySkillsTitle.isVisible = !vacancy.keySkills.isNullOrEmpty()
        binding.keySkills.isVisible = !vacancy.keySkills.isNullOrEmpty()
        vacancy.keySkills?.let { keyString ->
            binding.keySkills.text = keyString.split(",").joinToString(separator = "\n") { "• $it" }
        }
    }

    private fun fillContacts(vacancy: Vacancy) {
        binding.contactsTitle.isVisible =
            vacancy.contactsEmail?.isNotEmpty() ?: false || vacancy.contactsPhones?.isNotEmpty() ?: false
        binding.emailField.isVisible = vacancy.contactsEmail?.isNotEmpty() ?: false
        vacancy.contactsEmail?.let {
            binding.contactsEmail.text = it
            binding.contactsEmail.setOnClickListener {
                vacancyDetailsViewModel.openEmail(vacancy.contactsEmail, vacancy.name)
            }
        }
        binding.phoneNumberField.isVisible = vacancy.contactsPhones?.isNotEmpty() ?: false
        vacancy.contactsPhones?.let { contactsPhone ->
            val regex = "\\+[0-9]{11}".toRegex()
            val contactsFormattedNumber = regex.find(vacancy.contactsPhones)?.value
            val contactsComment =
                vacancy.contactsPhones.substringAfter("comment=").substringBefore(",")

            contactsFormattedNumber?.let {
                binding.contactsPhoneNumber.text = contactsFormattedNumber
                binding.contactsPhoneNumber.setOnClickListener {
                    vacancyDetailsViewModel.callPhone(contactsFormattedNumber)
                }
            }
            contactsComment?.let {
                binding.contactsPhoneComment.text = contactsComment
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        vacancyDetailsViewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        vacancyDetailsViewModel.observeShowToast().observe(viewLifecycleOwner) { showToast(it) }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    vacancyDetailsViewModel.makeVacancyFavorite()
                    true
                }

                R.id.share -> {
                    vacancyDetailsViewModel.shareVacancy()
                    true
                }

                else -> false
            }
        }
    }

    private fun showToast(toastMessage: String) {
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArgs(vacancy: Vacancy, vacancyNeedUpdate: Boolean): Bundle =
            bundleOf(
                Vacancy.EXTRAS_KEY to vacancy,
                VACANCY_NEED_TO_UPDATE_KEY to vacancyNeedUpdate
            )

        const val VACANCY_NEED_TO_UPDATE_KEY = "VACANCY_NEED_TO_UPDATE_KEY"
    }
}
