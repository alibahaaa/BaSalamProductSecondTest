package com.basalam.basalamproduct.util

/*
*********
* here we handle status of request that can be :
* Success which mean every thing works great , wow !
* Error which means we have some problem
* Loading which means its loading the data (Product list) that can work well or fail !
*********
 */

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}