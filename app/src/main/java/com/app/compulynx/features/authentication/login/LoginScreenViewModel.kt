package com.app.compulynx.features.authentication.login

import com.app.compulynx.core.base.BaseViewModel
import com.app.compulynx.core.base.UiEffect
import com.app.compulynx.core.base.UiEvent
import com.app.compulynx.core.base.UiState
import com.app.compulynx.core.base.validateCustomerId
import com.app.compulynx.core.base.validatePin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias ScreenModel = BaseViewModel<LoginScreenState, LoginScreenEvent, LoginScreenEffect>


@HiltViewModel
class LoginScreenViewModel @Inject constructor() : ScreenModel(LoginScreenState()) {
    override fun handleEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnCustomerIdChange -> {
                val id = event.id
                val customerIdError = validateCustomerId(id)
                setState { copy(customerId = id, customerIdError = customerIdError) }
                updateFormValidState()
            }

            is LoginScreenEvent.OnPinChange -> {
                val pin = event.pin
                val pinError = validatePin(pin)
                setState { copy(pin = pin, pinError = pinError) }
                updateFormValidState()
            }

            LoginScreenEvent.OnPinVisibleChange -> {
                setState { copy(isPinVisible = !isPinVisible) }
            }

            LoginScreenEvent.OnLoginButtonClick -> {
                if (validateForm()) {
                    signInUser()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        return true
    }

    private fun signInUser() {

    }

    private fun updateFormValidState() {

    }
}

data class LoginScreenState(
    val isLoading: Boolean = false,
    val customerId: String = "",
    val customerIdError: String? = null,
    val pin: String = "",
    val pinError: String? = null,
    val isPinVisible: Boolean = false,
    val isFormValid: Boolean = false
) : UiState


sealed interface LoginScreenEvent : UiEvent {
    data class OnCustomerIdChange(val id: String) : LoginScreenEvent
    data class OnPinChange(val pin: String) : LoginScreenEvent
    data object OnPinVisibleChange : LoginScreenEvent
    data object OnLoginButtonClick : LoginScreenEvent
}

sealed interface LoginScreenEffect : UiEffect {
    data object NavigateToHome : LoginScreenEffect
}