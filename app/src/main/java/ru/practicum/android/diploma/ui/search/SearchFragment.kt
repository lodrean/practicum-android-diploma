package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    var inputText: String = AMOUNT_DEF
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.inputEditText.setText(inputText)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.inputEditText.requestFocus()

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.inputEditText.requestFocus()
                binding.clearIcon.visibility = clearButtonVisibility(s)
                viewModel.searchDebounce(changedText = s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                inputText.plus(s)

            }
        }

        binding.inputEditText.addTextChangedListener(simpleTextWatcher)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun render(state: SearchState?): SearchState? {
        when (state) {
            is SearchState.Content -> TODO()
            is SearchState.Empty -> TODO()
            is SearchState.Error -> TODO()
            SearchState.Loading -> TODO()
            null -> TODO()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    companion object {
        const val AMOUNT_DEF = ""
        const val CLICK_DEBOUNCE_DELAY = 300L
    }

}
