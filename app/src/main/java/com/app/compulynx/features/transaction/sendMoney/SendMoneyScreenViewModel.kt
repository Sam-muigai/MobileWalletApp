package com.app.compulynx.features.transaction.sendMoney

import androidx.lifecycle.viewModelScope
import com.app.compulynx.core.base.BaseViewModel
import com.app.compulynx.core.base.SnackbarController
import com.app.compulynx.core.base.SnackbarEvent
import com.app.compulynx.core.base.UiEffect
import com.app.compulynx.core.base.UiEvent
import com.app.compulynx.core.base.UiState
import com.app.compulynx.core.base.Validator
import com.app.compulynx.core.work.status.SyncManager
import com.app.compulynx.domain.models.SendMoneyRequest
import com.app.compulynx.domain.repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


typealias ScreenModel = BaseViewModel<SendMoneyScreenState, SendMoneyScreenEvent, SendMoneyScreenEffect>

@HiltViewModel
class SendMoneyScreenViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val syncManager: SyncManager
) : ScreenModel(SendMoneyScreenState()) {
    override fun handleEvent(event: SendMoneyScreenEvent) {
        when (event) {
            is SendMoneyScreenEvent.OnAccountToChanged -> {
                val accountToError = Validator.validateAccountNo(event.accountTo)
                setState {
                    copy(
                        accountTo = event.accountTo,
                        accountToError = accountToError,
                        isFormValid = validateForm(
                            amount,
                            event.accountTo,
                            amountError,
                            accountToError
                        )
                    )
                }
            }

            is SendMoneyScreenEvent.OnAmountChanged -> {
                val digits = event.amount.filter { it.isDigit() }
                val amountError = Validator.validateAmount(digits)
                setState {
                    copy(
                        amount = digits,
                        amountError = amountError,
                        isFormValid = validateForm(
                            digits,
                            accountTo,
                            amountError,
                            accountToError
                        ),
                    )
                }
            }

            SendMoneyScreenEvent.OnSendMoneyClicked -> {
                sendMoney()
            }
        }
    }

    private fun sendMoney() {
        viewModelScope.launch {
            val sendMoneyRequest = SendMoneyRequest(
                amount = state.value.amount.toInt(),
                accountTo = state.value.accountTo
            )
            transactionRepository.saveLocalTransaction(sendMoneyRequest)
            syncManager.requestSync()
            SnackbarController.sendEvent(SnackbarEvent(message = "Transaction queued for processing successfully"))
            sendEffect(SendMoneyScreenEffect.NavigateBack)
        }
    }

    private fun validateForm(
        amount: String,
        accountTo: String,
        amountErr: String?,
        accountToErr: String?
    ): Boolean {
        return amountErr == null && accountToErr == null && amount.isNotEmpty() && accountTo.isNotEmpty()
    }
}

data class SendMoneyScreenState(
    val isLoading: Boolean = false,
    val amount: String = "",
    val amountError: String? = null,
    val accountTo: String = "",
    val accountToError: String? = null,
    val isFormValid: Boolean = false
) : UiState


sealed class SendMoneyScreenEvent : UiEvent {
    data class OnAmountChanged(val amount: String) : SendMoneyScreenEvent()
    data class OnAccountToChanged(val accountTo: String) : SendMoneyScreenEvent()
    data object OnSendMoneyClicked : SendMoneyScreenEvent()
}

sealed class SendMoneyScreenEffect : UiEffect {
    object NavigateBack : SendMoneyScreenEffect()
}