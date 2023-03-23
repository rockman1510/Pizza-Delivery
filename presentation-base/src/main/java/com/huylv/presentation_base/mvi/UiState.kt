package com.huylv.presentation_base.mvi

sealed class UiState<out RESULT : Any> {
    object Loading : UiState<Nothing>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
    data class Success<RESULT : Any>(val data: RESULT) : UiState<RESULT>()

}
