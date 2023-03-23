package com.huylv.data_repository.repository_impl

import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import com.huylv.domain.entity.Flavor
import com.huylv.domain.repository.FlavorRepository
import kotlinx.coroutines.flow.Flow

class FlavorRepositoryImpl(
    private val remoteFlavorDataSource: RemoteFlavorDataSource
) : FlavorRepository {
    override suspend fun getFlavors(): Flow<List<Flavor>> {
        return remoteFlavorDataSource.getFlavors()
    }
}