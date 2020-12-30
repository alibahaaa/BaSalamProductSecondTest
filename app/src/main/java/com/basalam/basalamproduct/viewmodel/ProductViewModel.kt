package com.basalam.basalamproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val query: String
) : ViewModel() {
    val cachedResponse: LiveData<List<Product>> = productRepository.getProduct(query)

//    if we use below code , our view model does not work az expected , so we use above code
//    var responseLiveData: LiveData<List<Product>> = MutableLiveData()
//    fun getResponseLiveData() {
//        viewModelScope.launch {
//            responseLiveData = productRepository.getProduct(query)
//        }
//    }
}