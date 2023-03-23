package com.huylv.presentation_flavor.mvi

import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.model.PizzaModel

class FlavorConverterUtils {

    companion object {
        fun convertFlavorToPizza(data: List<FlavorModel>?): PizzaModel {
            var price = 0F
            data?.forEach {
                price += (it.price * it.quantity)
            }
            return PizzaModel(data, price)
        }

    }


}