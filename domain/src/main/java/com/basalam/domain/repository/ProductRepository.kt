package com.basalam.domain.repository

import com.basalam.domain.entities.ProductEntity
import com.basalam.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProduct(): Flow<DataState<Flow<List<ProductEntity>>>>
}