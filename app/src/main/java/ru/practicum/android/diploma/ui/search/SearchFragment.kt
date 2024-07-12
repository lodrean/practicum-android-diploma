package ru.practicum.android.diploma.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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

    private var onItemClickListener: OnItemClickListener? = null
    private var onVacancyClickDebounce: (Vacancy) -> Unit = {}
    private var vacancyAdapter: VacancyAdapter? = null
    private var vacancyList: MutableList<Vacancy> = mutableListOf()
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
            CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false
        ) { vacancy ->
            launchVacancyDetails(vacancy)
        }
        onItemClickListener = OnItemClickListener { vacancy ->
            onVacancyClickDebounce(vacancy)
        }

        binding.clearIcon.setOnClickListener {
            vacancyList.clear()
            binding.inputEditText.setText(getString(R.string.empty_string))
            vacancyAdapter?.notifyDataSetChanged()
            defaultState()
            val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.clearIcon.windowToken, 0)
        }

        vacancyList = mutableListOf()
        vacancyAdapter = VacancyAdapter(vacancyList, onItemClickListener!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = vacancyAdapter

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.inputEditText.requestFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.isVisible = clearButtonVisibility(s)
                binding.inputEditText.requestFocus()
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
            is SearchState.Content -> state.countOfVacancies?.let { showContent(state.vacanciesList, it) }
            is SearchState.Empty -> showEmpty(state.message)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Loading -> showLoading()
            is SearchState.NoInternet -> showLooseInternetConnection(state.errorMessage)
        }
    }

    private fun showLooseInternetConnection(errorMessage: String) {
        showImageAndTextState()
        binding.vacancyCountTextView.isVisible = false
        binding.centralImageHolder.setImageResource(R.drawable.no_internet_image)
        binding.stateTextView.text = errorMessage
    }

    private fun showLoading() {
        binding.vacancyCountTextView.isVisible = false
        binding.progressBar.isVisible = true
        binding.centralImageHolder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = false
    }

    private fun showError(errorMessage: String) {
        showImageAndTextState()
        binding.vacancyCountTextView.isVisible = false
        binding.centralImageHolder.setImageResource(R.drawable.error_image)
        binding.stateTextView.text = errorMessage
    }

    private fun defaultState() {
        binding.vacancyCountTextView.isVisible = false
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = false
        binding.centralImageHolder.setImageResource(R.drawable.empty_search_image)
    }

    private fun showEmpty(message: String) {
        showImageAndTextState()
        binding.vacancyCountTextView.isVisible = true
        binding.vacancyCountTextView.text = getString(R.string.no_vacancies)
        binding.centralImageHolder.setImageResource(R.drawable.empty_list_image)
        binding.stateTextView.text = message
    }

    private fun showImageAndTextState() {
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.stateTextView.isVisible = true
    }

    private fun showContent(vacanciesList: List<Vacancy>, countOfVacancies: Int) {
        vacancyList.clear()
        binding.progressBar.isVisible = false
        binding.centralImageHolder.isVisible = false
        binding.recyclerView.isVisible = true
        binding.stateTextView.isVisible = false
        binding.vacancyCountTextView.isVisible = true
        binding.vacancyCountTextView.text = buildString {
            append(getString(R.string.Founded))
            append(
                context?.resources?.getQuantityString(
                    R.plurals.count_of_vacancies, countOfVacancies, countOfVacancies
                )
            )
        }
        vacancyList.addAll(vacanciesList)
        vacancyAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearButtonVisibility(s: CharSequence?): Boolean {
        return if (s.isNullOrEmpty()) {
            false
        } else {
            true
        }
    }

    companion object {
        const val AMOUNT_DEF = ""
        const val CLICK_DEBOUNCE_DELAY = 300L
    }

}
