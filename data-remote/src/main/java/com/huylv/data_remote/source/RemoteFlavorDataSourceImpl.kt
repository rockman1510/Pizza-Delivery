package com.huylv.data_remote.source

import com.huylv.data_remote.network.flavor.model.FlavorApiModel
import com.huylv.data_remote.network.flavor.service.FlavorService
import com.huylv.data_repository.data_source.remote.RemoteFlavorDataSource
import com.huylv.domain.entity.Flavor
import com.huylv.domain.entity.UseCaseException
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RemoteFlavorDataSourceImpl @Inject constructor(private val flavorService: FlavorService) :
    RemoteFlavorDataSource {
    override suspend fun getFlavors(): Flow<List<Flavor>> {
        return flow {
            emit(flavorService.getFlavors())
        }.map { flavors ->
            flavors.map { flavorApiModel ->
                convert(flavorApiModel)
            }
        }.catch {
            throw UseCaseException.FlavorException(it)
        }
    }

    private fun convert(flavorApiModel: FlavorApiModel): Flavor {
        return Flavor(flavorApiModel.name, flavorApiModel.price)
    }
}