package com.basalam.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.basalam.basalamproduct.GetProductsQuery
import com.basalam.data.mapper.ApiMapper
import com.basalam.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ProductRemoteImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val apiMapper: ApiMapper
) : ProductRemoteStore {
    override suspend fun getProduct(): Flow<List<ProductEntity>> {
        return flow {
            try {
                val response: Response<GetProductsQuery.Data> =
                    apolloClient.query(GetProductsQuery(size = 5)).await()
                emit(
                    apiMapper.mapFromEntities(
                        (response.data?.productSearch?.products as List<GetProductsQuery.Product>?)!!
                    )
                )
            } catch (e: ApolloException) {
                println("log connection error $e")
            }
        }
    }
}