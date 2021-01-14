package com.basalam.basalamproduct.di

import com.apollographql.apollo.ApolloClient
import com.basalam.basalamproduct.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApi(): ApolloClient {
        val apolloClient: ApolloClient by lazy {
            ApolloClient.builder()
                .serverUrl(Constants.BASE_URL)
                .build()
        }
        return apolloClient
    }
}