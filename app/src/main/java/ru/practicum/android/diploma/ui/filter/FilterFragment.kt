package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.presentation.filter.FilterState
import ru.practicum.android.diploma.presentation.filter.FilterViewModel

class FilterFragment : Fragment() {

    private var textWatcher: TextWatcher? = null
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModel<FilterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.topAppBar.setNavigationOnClickListener {
            viewModel.resetTheChanges()
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.resetTheChanges()
                    findNavController().popBackStack()
                }
            }
        )
        binding.workPlaceValue.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
        }
        binding.industryValue.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
        }
        binding.workPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
        }
        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
        }

        val (emptyHintColor, blackHintColor, blueHintColor) = hintColorStates()

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (clearButtonVisibility(s)) {
                    binding.salaryFrame.setEndIconDrawable(R.drawable.close_icon)
                    binding.salaryFrame.defaultHintTextColor = blueHintColor
                } else {
                    binding.salaryFrame.endIconDrawable = null
                    binding.salaryFrame.defaultHintTextColor = emptyHintColor
                }
                onFocusChangeListener(s)
            }

            override fun afterTextChanged(s: Editable?) {
                onFocusChangeListener(s)
                viewModel.setSalary(s.toString())
            }
        }

        binding.salaryValue.addTextChangedListener(textWatcher!!)
        binding.salaryFrame.setEndIconOnClickListener {
            binding.salaryValue.setText(getString(R.string.empty_string))
            binding.salaryFrame.endIconDrawable = null
            viewModel.clearSalary()
            it.hideKeyboard()
        }
        binding.resetButton.setOnClickListener {
            viewModel.clearFilter()
            binding.salaryFrame.defaultHintTextColor = emptyHintColor
        }

        binding.salaryIsRequiredCheck.setOnClickListener {
            if (checkSalaryRequired()) {
                viewModel.setSalaryIsRequired(true)
                viewModel.showSaveButton()
            } else {
                viewModel.setSalaryIsRequired(false)
            }
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveFilter(
                binding.workPlaceValue.text.toString(),
                binding.workPlaceValue.text.toString(),
                binding.salaryValue.text.toString(),
                checkSalaryRequired()
            )
            viewModel.saveNewFilter()
            findNavController().popBackStack()
        }
    }

    private fun checkSalaryRequired(): Boolean {
        return binding.salaryIsRequiredCheck.isChecked
    }

    private fun render(state: FilterState) {
        when (state) {
            FilterState.Default -> defaultScreen()
            is FilterState.Filtered -> filterScreen(state.filter)
            FilterState.readyToSave -> showSaveButton()
        }
    }

    private fun showSaveButton() {
        binding.resetButton.isVisible = true
        binding.saveButton.isVisible = true
    }

    private fun filterScreen(filter: Filter) {
        binding.workPlaceValue.setText(filter.area?.name)
        binding.industryValue.setText(filter.industry?.name)
        binding.salaryValue.setText(filter.salary)
        binding.salaryIsRequiredCheck.isChecked = filter.onlyWithSalary
        fillWorkPlace()
        fillIndustry()
        binding.saveButton.isVisible = true
        binding.resetButton.isVisible = true
        if (filter.salary?.isEmpty() == true) {
            binding.salaryFrame.defaultHintTextColor = hintColorStates().first
        } else {
            binding.salaryFrame.defaultHintTextColor = hintColorStates().second
        }
    }

    private fun fillWorkPlace() {
        if (binding.workPlaceValue.text.toString().isNotEmpty()) {
            binding.workPlace.defaultHintTextColor = setHintOnValueColor()
            binding.workPlace.setEndIconDrawable(R.drawable.close_icon)
            binding.workPlace.setEndIconOnClickListener {
                viewModel.clearWorkplace()
                binding.workPlaceValue.setText(getString(R.string.empty_string))
                binding.workPlace.setEndIconDrawable(R.drawable.arrow_forward)
                binding.workPlace.defaultHintTextColor = setGrayColor()
                binding.workPlace.setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
                }
            }
        } else {
            binding.workPlace.setEndIconDrawable(R.drawable.arrow_forward)
            binding.workPlace.setEndIconOnClickListener {
                findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
            }
        }
    }

    private fun fillIndustry() {
        if (binding.industryValue.text.toString().isNotEmpty()) {
            binding.industry.setEndIconDrawable(R.drawable.close_icon)
            binding.industry.defaultHintTextColor = setHintOnValueColor()
            binding.industry.setEndIconOnClickListener {
                viewModel.clearIndustry()
                binding.industryValue.setText(getString(R.string.empty_string))
                binding.industry.setEndIconDrawable(R.drawable.arrow_forward)
                binding.industry.defaultHintTextColor = setGrayColor()
                binding.industry.setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
                }
            }
        } else {
            binding.industry.setEndIconDrawable(R.drawable.arrow_forward)
            binding.industry.setEndIconOnClickListener {
                findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
            }
        }
    }

    private fun defaultScreen() {
        binding.resetButton.isVisible = false
        binding.saveButton.isVisible = false
        binding.workPlaceValue.setText(getString(R.string.empty_string))
        binding.industryValue.setText(getString(R.string.empty_string))
        binding.salaryValue.setText(getString(R.string.empty_string))
        binding.salaryIsRequiredCheck.isChecked = false
        binding.workPlace.setEndIconDrawable(R.drawable.arrow_forward)
        binding.industry.setEndIconDrawable(R.drawable.arrow_forward)
    }

    private fun hintColorStates(): Triple<ColorStateList, ColorStateList, ColorStateList> {
        val emptyHintColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color))
        )

        val blackHintColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color_after))
        )

        val blueHintColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color_blue))
        )
        return Triple(emptyHintColor, blackHintColor, blueHintColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGrayColor(): ColorStateList {
        val grayColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.gray))
        )
        return grayColor
    }

    private fun setHintOnValueColor(): ColorStateList {
        val blackOrWhiteColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_country_industry))
        )
        return blackOrWhiteColor
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }

    private fun onFocusChangeListener(s: CharSequence?) {
        binding.salaryValue.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.salaryFrame.defaultHintTextColor = hintColorStates().third
            } else {
                if (s?.isEmpty() == true) {
                    binding.salaryFrame.defaultHintTextColor = hintColorStates().first
                } else {
                    binding.salaryFrame.defaultHintTextColor = hintColorStates().second
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkNewFilter()
    }
}
