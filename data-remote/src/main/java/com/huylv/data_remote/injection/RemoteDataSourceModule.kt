package com.huylv.data_remote.injection

import com.huylv.data_remote.source.RemoteFlavorDataSourceImpl
import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindFlavorDataSource(flavorDataSourceImpl: RemoteFlavorDataSourceImpl): RemoteFlavorDataSource
}