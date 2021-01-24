package com.basalam.basalamproduct.di

import com.basalam.data.repository.ProductRemoteImpl
import com.basalam.data.repository.ProductRemoteStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideRemoteRepository(remoteImpl: ProductRemoteImpl): ProductRemoteStore {
        return remoteImpl
    }
}