package ru.practicum.android.diploma.ui.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.ui.adapters.IndustryAdapter

class IndustryFragment : Fragment() {
    private val industryViewModel by viewModel<IndustryViewModel>()
    private var industryAdapter: IndustryAdapter? = null

    private var _binding: FragmentIndustryBinding? = null
    private val binding
        get() = _binding!!

    private fun render(state: IndustryState) {
        when (state) {
            is IndustryState.Content -> {
                industryAdapter?.let {
                    showContent(state.industries)
                }
            }

            is IndustryState.Loading -> {
                showLoading()
            }

            is IndustryState.Empty -> {
                showEmpty(state.message)
            }

            is IndustryState.Error -> {
                showError(state.message)
            }
        }
    }

    private fun showContent(industryList: List<Industry>) {
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = false
        binding.recyclerView.isVisible = true
        binding.stateTextView.isVisible = false
        binding.selectButton.isVisible = false
        binding.recyclerView.post {
            kotlin.run {
                industryAdapter?.listData?.apply {
                    clear()
                    addAll(industryList)
                }
                industryAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.centralImageHolder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = false
        binding.selectButton.isVisible = false
    }

    private fun showEmpty(message: String) {
        showImageAndTextState()
        binding.centralImageHolder.setImageResource(R.drawable.empty_list_image)
        binding.stateTextView.text = message
    }

    private fun showError(errorMessage: String) {
        showImageAndTextState()
        binding.centralImageHolder.setImageResource(R.drawable.error_image)
        binding.stateTextView.text = errorMessage
    }

    private fun showImageAndTextState() {
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = true
        binding.selectButton.isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        industryAdapter = IndustryAdapter {
            binding.selectButton.isVisible = true
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = industryAdapter
        binding.recyclerView.animation = null

        binding.inputEditText.addTextChangedListener(
            onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                if (charSequence?.isNotEmpty() == true) {
                    binding.searchFrame.setEndIconDrawable(R.drawable.close_icon)
                    industryViewModel.searchDebounce(changedText = charSequence.toString())
                    binding.searchFrame.setEndIconOnClickListener {
                        binding.inputEditText.setText(getString(R.string.empty_string))
                        industryViewModel.loadIndustries()
                    }
                } else {
                    binding.searchFrame.setEndIconDrawable(R.drawable.search_icon)
                    binding.searchFrame.clearOnEndIconChangedListeners()
                }
            })
        binding.inputEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                industryViewModel.searchDebounce(changedText = textView.text.toString())
                true
            }
            false
        }

        industryViewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.selectButton.setOnClickListener {
            findNavController().popBackStack()
        }

        industryViewModel.loadIndustries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
