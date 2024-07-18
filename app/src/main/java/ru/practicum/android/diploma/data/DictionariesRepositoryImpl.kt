package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.AreaResponse
import ru.practicum.android.diploma.data.network.AreasRequest
import ru.practicum.android.diploma.data.network.CountriesRequest
import ru.practicum.android.diploma.data.network.CountriesResponse
import ru.practicum.android.diploma.data.network.IndustriesRequest
import ru.practicum.android.diploma.data.network.IndustriesResponse
import ru.practicum.android.diploma.domain.api.DictionariesRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.ErrorType
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.toArea
import ru.practicum.android.diploma.util.toIndustry

class DictionariesRepositoryImpl(
    private val context: Context,
    private val networkClient: NetworkClient,
) : DictionariesRepository {

    override fun getCountries(): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.doRequest(CountriesRequest())
        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(ErrorType.NoConnection)
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as CountriesResponse) {
                        Resource.Success(response.countries.map { it.toArea() })
                    }
                }

                else -> Resource.Error(
                    errorType = ErrorType.ServerError,
                    message = context.getString(R.string.server_error_message) +
                        " : ${response.resultCode}"
                )
            }
        )
    }

    override fun getRegionsByCountry(country: Area): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.doRequest(AreasRequest(country.id))
        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(ErrorType.NoConnection)
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as AreaResponse) {
                        Resource.Success(response.area.areas.map { it.toArea() })
                    }
                }

                else -> Resource.Error(
                    errorType = ErrorType.ServerError,
                    message = context.getString(R.string.server_error_message) +
                        " : ${response.resultCode}"
                )
            }
        )
    }

    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doRequest(IndustriesRequest())
        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(ErrorType.NoConnection)
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as IndustriesResponse) {
                        Resource.Success(response.industries.map { it.toIndustry() })
                    }
                }

                else -> Resource.Error(
                    errorType = ErrorType.ServerError,
                    message = context.getString(R.string.server_error_message) +
                        " : ${response.resultCode}"
                )
            }
        )
    }

}
