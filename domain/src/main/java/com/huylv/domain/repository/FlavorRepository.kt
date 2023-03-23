package com.huylv.domain.repository

import com.huylv.domain.entity.Flavor
import kotlinx.coroutines.flow.Flow

interface FlavorRepository {
    suspend fun getFlavors(): Flow<List<Flavor>>
}