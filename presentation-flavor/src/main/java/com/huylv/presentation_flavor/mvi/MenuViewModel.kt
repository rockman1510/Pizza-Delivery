package com.huylv.presentation_flavor.mvi

import androidx.lifecycle.viewModelScope
import com.huylv.domain.usecase.GetFlavorUseCase
import com.huylv.presentation_base.mvi.MVIViewModel
import com.huylv.presentation_base.mvi.UiState
import com.huylv.presentation_flavor.model.FlavorModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val flavorUseCase: GetFlavorUseCase,
    private val flavorConverter: FlavorConverter
) : MVIViewModel<List<FlavorModel>, UiState<List<FlavorModel>>, MenuUiAction, MenuUiSingleEvent>() {
    override fun initState(): UiState<List<FlavorModel>> {
        return UiState.Loading
    }

    override fun handleAction(action: MenuUiAction) {
        when (action) {
            is MenuUiAction.Load -> {
                loadFlavors()
            }
        }
    }

    private fun loadFlavors() {
        viewModelScope.launch {
            flavorUseCase.execute(GetFlavorUseCase.Request).map {
                flavorConverter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }
}