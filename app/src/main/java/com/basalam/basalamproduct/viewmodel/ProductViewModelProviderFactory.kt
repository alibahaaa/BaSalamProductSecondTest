package com.basalam.basalamproduct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.basalam.basalamproduct.repository.ProductRepository

class ProductViewModelProviderFactory(private val productRepository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(productRepository) as T
    }
}