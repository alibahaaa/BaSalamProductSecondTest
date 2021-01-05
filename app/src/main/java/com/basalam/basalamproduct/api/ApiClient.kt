package com.basalam.basalamproduct.api

import com.apollographql.apollo.ApolloClient
import com.basalam.basalamproduct.util.Constants.Companion.BASE_URL

class ApiClient {
    companion object {
        val apolloClient: ApolloClient by lazy {
            ApolloClient.builder()
                .serverUrl(BASE_URL)
                .build()
        }
    }
}