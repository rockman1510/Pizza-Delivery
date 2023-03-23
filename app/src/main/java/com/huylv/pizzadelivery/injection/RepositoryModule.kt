package com.huylv.pizzadelivery.injection

import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import com.huylv.data_repository.repository_impl.FlavorRepositoryImpl
import com.huylv.domain.repository.FlavorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideFlavorRepository(remoteFlavorDataSource: RemoteFlavorDataSource): FlavorRepository =
        FlavorRepositoryImpl(remoteFlavorDataSource)
}