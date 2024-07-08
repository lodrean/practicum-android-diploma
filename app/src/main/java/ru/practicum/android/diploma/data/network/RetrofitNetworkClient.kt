package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient

class RetrofitNetworkClient(private val context: Context) : NetworkClient {

    private val baseUrl = "https://api.hh.ru/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val hhService = retrofit.create(HHApi::class.java)

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = HTTP_NO_CONNECTION }
        }

        return when (dto) {
            is VacanciesRequest -> getVacancies(dto)
            is VacancyRequest -> getVacancyFull(dto)
            is CountriesRequest -> getCountries()
            is AreasRequest -> getAreas(dto)
            else -> Response().apply { resultCode = HTTP_CLIENT_ERROR }
        }
    }

    private suspend fun getVacancies(request: VacanciesRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                hhService.getVacancies(request.toMap())
                    .apply { resultCode = HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            }
        }
    }

    private suspend fun getCountries(): Response {
        return withContext(Dispatchers.IO) {
            try {
                CountriesResponse(hhService.getCountries())
                    .apply { resultCode = HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            }
        }
    }

    private suspend fun getAreas(request: AreasRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                AreasResponse(hhService.getAreas(request.areaId))
                    .apply { resultCode = HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            }
        }
    }

    private suspend fun getVacancyFull(request: VacancyRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                VacancyResponse(hhService.getVacancyFull(request.vacancyId))
                    .apply { resultCode = HTTP_SUCCESS }
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            }
        }
    }

    private fun VacanciesRequest.toMap(): Map<String, String> {
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

    companion object {
        private const val HTTP_NO_CONNECTION = -1
        private const val HTTP_CLIENT_ERROR = 400
        private const val HTTP_SUCCESS = 200
    }

}
