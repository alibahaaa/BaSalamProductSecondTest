package com.basalam.basalamproduct.di

import com.apollographql.apollo.ApolloClient
import com.basalam.basalamproduct.util.Constants
import dagger.Module
import dagger.Provides


@Module
class ApiModule {
    @Provides
    fun provideApi(): ApolloClient {
        val apolloClient: ApolloClient by lazy {
            ApolloClient.builder()
                .serverUrl(Constants.BASE_URL)
                .build()
        }
        return apolloClient
    }
}