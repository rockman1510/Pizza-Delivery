package com.huylv.pizzadelivery.data_remote

import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import com.huylv.domain.entity.Flavor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MockRemoteFlavorDataSource @Inject constructor() : RemoteFlavorDataSource {
    private val flavors = listOf(
        Flavor("Mozzarella", (10).toDouble()),
        Flavor("Pepperoni", (12).toDouble()),
        Flavor("Vegetarian", 9.5),
        Flavor("Super cheese", (11).toDouble()),
        Flavor("Napoli", 8.5),
        Flavor("Buffala", 12.5),
    )

    override suspend fun getFlavors(): Flow<List<Flavor>> {
        return flowOf(flavors)
    }
}