package com.basalam.domain.usecases

import com.basalam.domain.entities.ProductEntity
import com.basalam.domain.repository.ProductRepository
import com.basalam.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend fun getProductUseCase():Flow<DataState<Flow<List<ProductEntity>>>>{
        println("log useCase ")
        return repository.getProduct()
    }
}