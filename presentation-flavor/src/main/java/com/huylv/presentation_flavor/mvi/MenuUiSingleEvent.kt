package com.huylv.presentation_flavor.mvi

import com.huylv.presentation_base.mvi.UiEvent
import com.huylv.presentation_flavor.model.PizzaModel

sealed class MenuUiSingleEvent : UiEvent {
    data class OpenSummaryOrderScreen(val pizzaModel: PizzaModel) : MenuUiSingleEvent()
}