package com.app.compulynx.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>(
    private val initialState: State
) : ViewModel() {
    abstract fun handleEvent(event: Event)

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val currentState: State
        get() = state.value
    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()


    protected fun setState(reducer: State.() -> State) {
        _state.update { currentState.reducer() }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}