package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.DictionariesRequest
import ru.practicum.android.diploma.data.network.DictionariesResponse
import ru.practicum.android.diploma.data.network.VacanciesSearchRequest
import ru.practicum.android.diploma.data.network.VacanciesSearchResponse
import ru.practicum.android.diploma.data.network.VacancyRequest
import ru.practicum.android.diploma.data.network.VacancyResponse
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.toVacancy

class VacanciesRepositoryImpl(
    private val context: Context,
    private val networkClient: NetworkClient,
) : VacanciesRepository {
    private var currencyMap: Map<String, String>? = null

    override fun searchVacancies(
        expression: String,
        page: Int,
        perPage: Int,
    ): Flow<Resource<VacanciesSearchResult>> = flow {
        if (currencyMap == null) {
            currencyMap = loadCurrencyMap()
        }

        val response = networkClient.doRequest(
            VacanciesSearchRequest(
                text = expression,
                page = page,
                perPage = perPage,
            )
        )

        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(context.getString(R.string.check_connection_message))
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as VacanciesSearchResponse) {
                        Resource.Success(
                            VacanciesSearchResult(
                                vacancies = this.items.map { it ->
                                    val vacancy = it.toVacancy()
                                    currencyMap?.get(vacancy.salaryCurrencyName)?.let { newName ->
                                        vacancy.copy(salaryCurrencyName = newName)
                                    } ?: vacancy
                                },
                                page = this.page,
                                found = this.found,
                                count = this.pages
                            )
                        )
                    }
                }

                else -> Resource.Error(
                    context.getString(R.string.server_error_message) +
                        " : ${response.resultCode}"
                )
            }
        )

    }

    override fun updateToFullVacancy(vacancy: Vacancy): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequest(
            VacancyRequest(vacancyId = vacancy.id)
        )

        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(context.getString(R.string.check_connection_message))
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as VacancyResponse) {
                        Resource.Success(
                            vacancy.copy(
                                description = response.vacancy.description,
                                keySkills = response.vacancy.keySkills.joinToString { it.name },
                            )
                        )
                    }
                }

                else -> Resource.Error(
                    context.getString(R.string.server_error_message) +
                        " : ${response.resultCode}"
                )
            }
        )

    }

    private suspend fun loadCurrencyMap(): Map<String, String> {
        val response = networkClient.doRequest(DictionariesRequest())
        val currencyMap = emptyMap<String, String>().toMutableMap()
        if (response.resultCode == NetworkClient.HTTP_SUCCESS) {
            with(response as DictionariesResponse) {
                this.dictionaries.currency.forEach {
                    currencyMap[it.code] = it.abbr
                }
            }
        }
        return currencyMap
    }

}
