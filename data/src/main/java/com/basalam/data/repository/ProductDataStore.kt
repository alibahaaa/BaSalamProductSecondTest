package com.basalam.data.repository

import com.basalam.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductCacheStore {
    suspend fun getProduct(): Flow<List<ProductEntity>>
}

interface ProductRemoteStore {
    suspend fun getProduct(): Flow<List<ProductEntity>>
}
