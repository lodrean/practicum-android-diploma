package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import java.util.concurrent.TimeoutException

class RetrofitNetworkClient(
    private val context: Context,
    private val hhService: HHApi,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NetworkClient.HTTP_NO_CONNECTION }
        }

        return when (dto) {
            is VacanciesSearchRequest -> getVacancies(dto)
            is VacancyRequest -> getVacancyFull(dto)
            is CountriesRequest -> getCountries()
            is AreasByIdRequest -> getAreasById(dto)
            is AreasRequest -> getAreas()
            is IndustriesRequest -> getIndustries()
            else -> Response().apply { resultCode = NetworkClient.HTTP_CLIENT_ERROR }
        }
    }

    private suspend fun getVacancies(request: VacanciesSearchRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                hhService.getVacancies(request.toMap())
                    .apply { resultCode = NetworkClient.HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (e: TimeoutException) {
                Response().apply { resultCode = NetworkClient.HTTP_SERVER_TIMEOUT_ERROR }
            }
        }
    }

    private suspend fun getCountries(): Response {
        return withContext(Dispatchers.IO) {
            try {
                CountriesResponse(hhService.getCountries())
                    .apply { resultCode = NetworkClient.HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (e: TimeoutException) {
                Response().apply { resultCode = NetworkClient.HTTP_SERVER_TIMEOUT_ERROR }
            }
        }
    }

    private suspend fun getAreasById(request: AreasByIdRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                AreasByIdResponse(hhService.getAreasById(request.areaId))
                    .apply { resultCode = NetworkClient.HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (e: TimeoutException) {
                Response().apply { resultCode = NetworkClient.HTTP_SERVER_TIMEOUT_ERROR }
            }
        }
    }

    private suspend fun getAreas(): Response {
        return withContext(Dispatchers.IO) {
            try {
                AreaResponse(hhService.getAreas())
                    .apply { resultCode = NetworkClient.HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (e: TimeoutException) {
                Response().apply { resultCode = NetworkClient.HTTP_SERVER_TIMEOUT_ERROR }
            }
        }
    }

    private suspend fun getIndustries(): Response {
        return withContext(Dispatchers.IO) {
            try {
                IndustriesResponse(hhService.getIndustries())
                    .apply { resultCode = NetworkClient.HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (e: TimeoutException) {
                Response().apply { resultCode = NetworkClient.HTTP_SERVER_TIMEOUT_ERROR }
            }
        }
    }

    private suspend fun getVacancyFull(request: VacancyRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                VacancyResponse(hhService.getVacancyFull(request.vacancyId))
                    .apply { resultCode = NetworkClient.HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (e: TimeoutException) {
                Response().apply { resultCode = NetworkClient.HTTP_SERVER_TIMEOUT_ERROR }
            }
        }
    }

    private fun VacanciesSearchRequest.toMap(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        if (text.isNotEmpty()) {
            map["text"] = text
        }
        if (page > 0) {
            map["page"] = page.toString()
        }
        if (perPage > 0) {
            map["per_page"] = perPage.toString()
        }

        filter.area?.let { map["area"] = it.id }
        filter.industry?.let { map["industry"] = it.id }
        filter.salary?.let { map["salary"] = it.toString() }
        map["only_with_salary"] = filter.onlyWithSalary.toString()

        return map
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

}
