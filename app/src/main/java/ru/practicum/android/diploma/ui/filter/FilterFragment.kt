package ru.practicum.android.diploma.ui.filter

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private var textWatcher: TextWatcher? = null
    private var _binding: FragmentFilterBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.workPlaceTv.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
        }
        binding.industryTv.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
        }
        binding.workPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_workplace_fragment)
        }
        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.action_filter_fragment_to_industry_fragment)
        }



        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.salaryFrame.setEndIconDrawable(R.drawable.close_icon)

                    val colorStateList = ColorStateList(
                        arrayOf(intArrayOf(android.R.attr.state_enabled)),
                        intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color_after))
                    )
                    binding.salaryFrame.hintTextColor = colorStateList
                }
                else {
                    val colorStateList = ColorStateList(
                        arrayOf(intArrayOf(android.R.attr.state_enabled)),
                        intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color))
                    )
                    binding.salaryFrame.hintTextColor = colorStateList
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {

                    val colorStateList = ColorStateList(
                        arrayOf(intArrayOf(android.R.attr.state_enabled)),
                        intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color_after))
                    )
                    binding.salaryFrame.hintTextColor = colorStateList
                }
                else {
                    val colorStateList = ColorStateList(
                        arrayOf(intArrayOf(android.R.attr.state_enabled)),
                        intArrayOf(ContextCompat.getColor(requireContext(), R.color.text_hint_color))
                    )
                    binding.salaryFrame.hintTextColor = colorStateList
                }
            }

        }

        binding.salaryTv.addTextChangedListener(textWatcher!!)
        binding.salaryFrame.setEndIconOnClickListener {
            binding.salaryTv.setText(getString(R.string.empty_string))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
