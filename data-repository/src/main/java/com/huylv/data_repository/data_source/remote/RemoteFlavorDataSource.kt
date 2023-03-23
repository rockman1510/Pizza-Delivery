package com.huylv.data_repository.data_source.remote

import com.huylv.domain.entity.Flavor
import kotlinx.coroutines.flow.Flow

interface RemoteFlavorDataSource {
    suspend fun getFlavors(): Flow<List<Flavor>>
}