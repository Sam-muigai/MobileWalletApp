package com.app.compulynx.features.authentication.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.app.compulynx.core.base.BaseViewModel
import com.app.compulynx.core.base.SnackbarController
import com.app.compulynx.core.base.SnackbarEvent
import com.app.compulynx.core.base.UiEffect
import com.app.compulynx.core.base.UiEvent
import com.app.compulynx.core.base.UiState
import com.app.compulynx.core.base.Validator
import com.app.compulynx.domain.models.LoginRequest
import com.app.compulynx.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ScreenModel = BaseViewModel<LoginScreenState, LoginScreenEvent, LoginScreenEffect>


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ScreenModel(LoginScreenState()) {

    override fun handleEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnCustomerIdChange -> {
                val error = Validator.validateCustomerId(event.id)
                setState {
                    copy(
                        customerId = event.id,
                        customerIdError = error,
                        isFormValid = validateForm(event.id, pin, error, pinError)
                    )
                }
            }

            is LoginScreenEvent.OnPinChange -> {
                val pinNumber = event.pin.filter { it.isDigit() }
                val error = Validator.validatePin(pinNumber)
                setState {
                    copy(
                        pin = pinNumber,
                        pinError = error,
                        isFormValid = validateForm(customerId, pinNumber, customerIdError, error)
                    )
                }
            }

            LoginScreenEvent.OnPinVisibleChange -> {
                setState { copy(isPinVisible = !isPinVisible) }
            }

            LoginScreenEvent.OnLoginButtonClick -> {
                signInUser()
            }
        }
    }

    private fun signInUser() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val loginRequest = LoginRequest(state.value.customerId, state.value.pin)
            authRepository.login(loginRequest)
                .onSuccess { message ->
                    setState { copy(isLoading = false) }
                    sendEffect(LoginScreenEffect.NavigateToHome)
                    SnackbarController.sendEvent(SnackbarEvent(message))
                }.onFailure { e ->
                    setState { copy(isLoading = false) }
                    SnackbarController.sendEvent(SnackbarEvent(e.message ?: "Something went wrong"))
                }
        }
    }

    private fun validateForm(id: String, pin: String, idErr: String?, pinErr: String?): Boolean {
        return idErr == null && pinErr == null && id.isNotEmpty() && pin.isNotEmpty()
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