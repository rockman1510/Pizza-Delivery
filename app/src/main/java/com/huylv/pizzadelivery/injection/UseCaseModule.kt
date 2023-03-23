package com.huylv.pizzadelivery.injection

import com.huylv.domain.repository.FlavorRepository
import com.huylv.domain.usecase.BaseUseCase
import com.huylv.domain.usecase.GetFlavorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCaseConfiguration(): BaseUseCase.Configuration =
        BaseUseCase.Configuration(Dispatchers.IO)

    @Provides
    fun provideGetFlavorUseCase(
        configuration: BaseUseCase.Configuration,
        flavorRepository: FlavorRepository
    ): GetFlavorUseCase = GetFlavorUseCase(configuration, flavorRepository)
}