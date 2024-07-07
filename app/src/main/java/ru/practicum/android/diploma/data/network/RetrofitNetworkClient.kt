package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            return Response().apply { resultCode = -1 }
        }

        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesRequest -> hhService.getVacancies(dto.toMap())
                        .apply { resultCode = 200 }

                    is CountriesRequest -> CountriesResponse(hhService.getCountries())
                        .apply { resultCode = 200 }

                    is AreasRequest -> AreasResponse(hhService.getAreas(dto.areaId))
                        .apply { resultCode = 200 }

                    else -> Response().apply { resultCode = 400 }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
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
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

}
