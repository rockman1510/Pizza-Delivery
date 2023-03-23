package com.huylv.presentation_flavor.mvi

import android.content.Context
import com.huylv.domain.usecase.GetFlavorUseCase
import com.huylv.presentation_base.mvi.BaseResultConverter
import com.huylv.presentation_flavor.model.FlavorModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FlavorConverter @Inject constructor(@ApplicationContext private val context: Context) :
    BaseResultConverter<GetFlavorUseCase.Response, List<FlavorModel>>() {

    override fun convertSuccess(data: GetFlavorUseCase.Response): List<FlavorModel> {
        return data.flavors.map {
            FlavorModel(it.name, it.price, 0F)
        }
    }
}