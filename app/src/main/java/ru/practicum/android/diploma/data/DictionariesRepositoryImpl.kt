package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.network.AreaResponse
import ru.practicum.android.diploma.data.network.AreasByIdRequest
import ru.practicum.android.diploma.data.network.AreasByIdResponse
import ru.practicum.android.diploma.data.network.AreasRequest
import ru.practicum.android.diploma.data.network.IndustriesRequest
import ru.practicum.android.diploma.data.network.IndustriesResponse
import ru.practicum.android.diploma.data.network.Response
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
    private fun makeErrorMessage(response: Response): String {
        val header = context.getString(R.string.server_error_message)
        return "$header : ${response.resultCode}"
    }

    override fun getAreas(): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.doRequest(AreasRequest())
        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(ErrorType.NoConnection)
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as AreaResponse) {
                        Resource.Success(response.area.map {
                            it.toArea()
                        })
                    }
                }

                else -> Resource.Error(
                    errorType = ErrorType.ServerError,
                    message = makeErrorMessage(response)
                )
            }
        )
    }

    override fun getAreasById(areaId: String): Flow<Resource<Area>> = flow {
        val response = networkClient.doRequest(AreasByIdRequest(areaId))
        emit(
            when (response.resultCode) {
                NetworkClient.HTTP_NO_CONNECTION -> Resource.Error(ErrorType.NoConnection)
                NetworkClient.HTTP_SUCCESS -> {
                    with(response as AreasByIdResponse) {
                        Resource.Success(response.area.toArea())
                    }
                }

                else -> Resource.Error(
                    errorType = ErrorType.ServerError,
                    message = makeErrorMessage(response)
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
                        val industries = mutableListOf<Industry>()
                        addSubIndustries(industries, response.industries)
                        Resource.Success(industries)
                    }
                }

                else -> Resource.Error(
                    errorType = ErrorType.ServerError,
                    message = makeErrorMessage(response)
                )
            }
        )
    }

    private fun addSubIndustries(industries: MutableList<Industry>, industriesDto: List<IndustryDto>) {
        industriesDto.forEach {
            industries.add(it.toIndustry())
            it.industries?.let { subIndustries ->
                addSubIndustries(industries, subIndustries)
            }
        }
    }

}
