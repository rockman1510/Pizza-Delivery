package com.huylv.presentation_base.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MVIViewModel<DATA : Any, STATE : UiState<DATA>, ACTION : UiAction, EVENT : UiEvent> :
    ViewModel() {

    private val _uiStateFlow: MutableStateFlow<STATE> by lazy { MutableStateFlow(initState()) }
    val uiStateFlow: StateFlow<STATE> = _uiStateFlow.asStateFlow()
    private val actionFlow: MutableSharedFlow<ACTION> = MutableSharedFlow()
    private val _singleEventChannel = Channel<EVENT>()
    val singleEventFlow = _singleEventChannel.receiveAsFlow()

    abstract fun initState(): STATE

    abstract fun handleAction(action: ACTION)

    init {
        viewModelScope.launch {
            actionFlow.collect {
                handleAction(it)
            }
        }
    }

    fun submitAction(action: ACTION) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    fun submitState(state: STATE) {
        viewModelScope.launch {
            _uiStateFlow.emit(state)
        }
    }

    fun submitSingleEvent(event: EVENT) {
        viewModelScope.launch {
            _singleEventChannel.send(event)
        }
    }
}