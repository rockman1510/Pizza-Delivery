package com.huylv.presentation_flavor.mvi

import com.huylv.domain.usecase.GetFlavorUseCase
import com.huylv.presentation_base.mvi.BaseResultConverter
import com.huylv.presentation_flavor.model.FlavorModel
import javax.inject.Inject

class FlavorConverter @Inject constructor() :
    BaseResultConverter<GetFlavorUseCase.Response, List<FlavorModel>>() {

    override fun convertSuccess(data: GetFlavorUseCase.Response): List<FlavorModel> {
        return data.flavors.map {
            FlavorModel(it.name, it.price)
        }
    }
}