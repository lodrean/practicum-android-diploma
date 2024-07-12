package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.VacancyAdapter
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsFragment
import ru.practicum.android.diploma.util.OnItemClickListener
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {

    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onVacancyClickDebounce: (Vacancy) -> Unit
    private lateinit var vacancyAdapter: VacancyAdapter
    private lateinit var vacancyList: MutableList<Vacancy>
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
        defaultState()
        onVacancyClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            launchVacancyDetails(vacancy)
        }
        onItemClickListener = OnItemClickListener { vacancy ->
            onVacancyClickDebounce(vacancy)
        }

        vacancyList = mutableListOf()
        vacancyAdapter = VacancyAdapter(vacancyList, onItemClickListener)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = vacancyAdapter

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

    private fun launchVacancyDetails(vacancy: Vacancy) {
        findNavController().navigate(
            R.id.action_search_fragment_to_vacancy_details_fragment,
            VacancyDetailsFragment.createArgs(vacancy)
        )
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Content -> showContent(state.vacanciesList)
            is SearchState.Empty -> showEmpty(state.message)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Loading -> showLoading()
            is SearchState.NoInternet -> showLooseInternetConnection(state.errorMessage)
        }
    }

    private fun showLooseInternetConnection(errorMessage: String) {
        showImageAndTextState()
        binding.centralImageHolder.setImageResource(R.drawable.no_internet_image)
        binding.stateTextView.text = errorMessage
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.centralImageHolder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = false
    }

    private fun showError(errorMessage: String) {
        showImageAndTextState()
        binding.centralImageHolder.setImageResource(R.drawable.error_image)
        binding.stateTextView.text = errorMessage
    }

    private fun defaultState() {
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = false
        binding.centralImageHolder.setImageResource(R.drawable.empty_search_image)
    }

    private fun showEmpty(message: String) {
        showImageAndTextState()
        binding.centralImageHolder.setImageResource(R.drawable.empty_list_image)
        binding.stateTextView.text = message
    }

    private fun showImageAndTextState() {
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = true
    }

    private fun showContent(vacanciesList: List<Vacancy>) {
        vacancyList.clear()
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = false
        binding.recyclerView.isVisible = true
        binding.stateTextView.isVisible = false
        vacancyList.addAll(vacanciesList)
        vacancyAdapter.notifyDataSetChanged()
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
