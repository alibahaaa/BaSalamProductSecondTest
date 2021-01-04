package com.basalam.basalamproduct.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)

    sealed class Error<T>(
        message: String, data: T? = null
    ) : Resource<T>(data, message) {
        class Internal<T>(message: String, data: T? = null) : Error<T>(message, data)
        class Validator<T>(message: String, data: T? = null) : Error<T>(message, data)
        class UnKnownError<T>(message: String, data: T? = null) : Error<T>(message, data)
    }

    class Empty<T> : Resource<T>()

    class Loading<T> : Resource<T>()
}