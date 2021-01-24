package com.basalam.data.repository

import com.basalam.data.mapper.ToEntityMapper
import com.basalam.domain.entities.ProductEntity
import com.basalam.domain.repository.ProductRepository
import com.basalam.domain.utils.DataState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productCacheImpl: ProductCacheImpl,
    private val productRemoteImpl: ProductRemoteImpl,
    private val toEntityMapper: ToEntityMapper
) : ProductRepository {
    override suspend fun getProduct(): Flow<DataState<Flow<List<ProductEntity>>>> {
        println("log repository")
        return flow {
            emit(DataState.Success(productCacheImpl.getProduct()))
            val response = productRemoteImpl.getProduct()
            response.collect {
                if (it.isNotEmpty()) {
                    productCacheImpl.insertProduct(toEntityMapper.mapFromEntities(it))
                    emit(DataState.Success(productCacheImpl.getProduct()))
                    if (productCacheImpl.productSize() == 0) {
                        emit(DataState.Empty())
                        //someCode
                    }
                } else {
                    emit(
                        DataState.Error(
                            productCacheImpl.getProduct(),
                            "در سمت سرور خطایی رخ داده است",
                            "UnKnownError"
                        )
                    )
                }
            }
        }
    }
}