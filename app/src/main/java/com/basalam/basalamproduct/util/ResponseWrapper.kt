package com.basalam.basalamproduct.util

import androidx.lifecycle.LiveData
import com.basalam.basalamproduct.model.Product

interface ResponseWrapper {
    fun onResponse(response: DataState<LiveData<List<Product>>>)
}