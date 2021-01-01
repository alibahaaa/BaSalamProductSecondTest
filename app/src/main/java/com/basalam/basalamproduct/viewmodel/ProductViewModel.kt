package com.basalam.basalamproduct.viewmodel

import androidx.lifecycle.*
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.Resource

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val query: String
) : ViewModel() {
    val resource: MutableLiveData<Resource<LiveData<List<Product>>>> =
        productRepository.getProduct(query)
}