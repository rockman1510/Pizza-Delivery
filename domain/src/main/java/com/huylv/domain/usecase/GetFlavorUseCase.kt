package com.huylv.domain.usecase

import com.huylv.domain.entity.Flavor
import com.huylv.domain.repository.FlavorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFlavorUseCase(
    configuration: BaseUseCase.Configuration,
    private val flavorRepository: FlavorRepository
) : BaseUseCase<GetFlavorUseCase.Request, GetFlavorUseCase.Response>(configuration) {

    override suspend fun process(request: Request): Flow<Response> {
        return flavorRepository.getFlavors().map {
            Response(it)
        }
    }

    object Request : BaseUseCase.Request
    data class Response(val flavors: List<Flavor>) : BaseUseCase.Response
}