package com.basalam.basalamproduct.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)

    sealed class Error<T>(
        message: String, data: T? = null
    ) : Resource<T>(data, message) {
        class ClientError<T>(message: String, data: T? = null) : Error<T>(message, data)
        class ServerError<T>(message: String, data: T? = null) : Error<T>(message, data)
        class UnKnownError<T>(message: String, data: T? = null) : Error<T>(message, data)
    }

    class Loading<T> : Resource<T>()

    class Empty<T> : Resource<T>()
}