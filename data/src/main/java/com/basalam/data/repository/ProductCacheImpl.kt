package com.basalam.data.repository


import com.basalam.data.db.ProductDao
import com.basalam.data.db.ProductDatabase
import com.basalam.data.entities.Product
import com.basalam.data.mapper.CacheMapper
import com.basalam.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductCacheImpl @Inject constructor(
    private val database: ProductDatabase,
    private val cacheMapper: CacheMapper
) : ProductCacheStore {
    private val dao: ProductDao = database.getProductDao()
    private val mapper: CacheMapper = cacheMapper

    override suspend fun getProduct(): Flow<List<ProductEntity>> {
        return dao.getAllProducts().map {
            mapper.mapFromEntities(it)
        }
    }

    suspend fun insertProduct(products: List<Product>) {
        dao.deleteAllProducts()
        dao.insert(products)
    }

    suspend fun productSize(): Int {
        return dao.getProductSize()
    }
}



