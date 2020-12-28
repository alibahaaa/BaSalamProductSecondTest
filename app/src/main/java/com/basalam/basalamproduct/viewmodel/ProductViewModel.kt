package com.basalam.basalamproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products: MutableLiveData<DataState<List<Product>>> = MutableLiveData()
    val product: LiveData<DataState<List<Product>>>
        get() = _products

    fun setStateEvent(mainStateEvents: MainStateEvents, query: String) {
        viewModelScope.launch {
            when (mainStateEvents) {
                is MainStateEvents.GetProductEvents -> {
                    productRepository.getProduct(query)
                        .onEach { product ->
                            _products.value = product
                            _products.postValue(product)
                        }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MainStateEvents {
    object GetProductEvents : MainStateEvents()
}