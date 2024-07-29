package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val errorType: ErrorType? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorType: ErrorType, message: String? = null, data: T? = null) :
        Resource<T>(data = data, errorType = errorType, message = message)
}
