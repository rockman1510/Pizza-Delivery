package com.huylv.presentation_flavor.mvi

import com.huylv.presentation_base.mvi.UiAction

sealed class MenuUiAction : UiAction {
    object Load : MenuUiAction()
}