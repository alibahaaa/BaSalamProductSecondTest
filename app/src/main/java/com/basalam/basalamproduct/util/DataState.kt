package com.basalam.basalamproduct.util

sealed class DataState<T>(
    val data: T? = null,
    val message: String? = null,
    val categoryError: String? = null
) {
    class Success<T>(data: T) : DataState<T>(data)
    class Empty<T> : DataState<T>()
    class Error<T>(data: T, message: String?, categoryError: String?) :
        DataState<T>(data, message, categoryError)
}