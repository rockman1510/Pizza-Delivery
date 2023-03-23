package com.huylv.pizzadelivery.injection

import com.huylv.data_remote.injection.RemoteDataSourceModule
import com.huylv.data_remote.source.RemoteFlavorDataSourceImpl
import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import com.huylv.pizzadelivery.data_remote.MockRemoteFlavorDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteDataSourceModule::class]
)
abstract class MockRemoteDataSourceModule {

    @Binds
    abstract fun bindFlavorDataSource(flavorDataSource: MockRemoteFlavorDataSource): RemoteFlavorDataSource
}