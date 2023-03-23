package com.huylv.presentation_base.mvi

import com.huylv.domain.entity.Result

abstract class BaseResultConverter<INTPUT : Any, OUTPUT : Any> {

    fun convert(result: Result<INTPUT>): UiState<OUTPUT> {
        return when (result) {
            is Result.Error -> {
                UiState.Error(result.exception.message.orEmpty())
            }
            is Result.Success -> {
                UiState.Success(convertSuccess(result.data))
            }
        }
    }

    abstract fun convertSuccess(data: INTPUT): OUTPUT
}