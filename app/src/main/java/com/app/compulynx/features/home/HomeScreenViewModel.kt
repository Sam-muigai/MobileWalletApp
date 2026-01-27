package com.app.compulynx.features.home

import androidx.lifecycle.viewModelScope
import com.app.compulynx.core.base.BaseViewModel
import com.app.compulynx.core.base.SnackbarController
import com.app.compulynx.core.base.SnackbarEvent
import com.app.compulynx.core.base.UiEffect
import com.app.compulynx.core.base.UiEvent
import com.app.compulynx.core.base.UiState
import com.app.compulynx.domain.models.Transaction
import com.app.compulynx.domain.repositories.AccountRepository
import com.app.compulynx.domain.repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


typealias ScreenModel = BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ScreenModel(HomeScreenState()) {

    init {
        viewModelScope.launch {
            val username = accountRepository.getUsername().first()
            setState { copy(username = username) }
            getMiniStatement()
        }
    }


    override fun handleEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.OnViewBalanceClick -> {
                fetchAccountDetails()
            }
        }
    }

    private suspend fun getMiniStatement() {
        setState { copy(isTransactionLoading = true) }
        transactionRepository.getMiniStatement()
            .onSuccess { transactions ->
                setState {
                    copy(
                        isTransactionLoading = false,
                        transactions = transactions
                    )
                }
            }.onFailure {
                setState { copy(isTransactionLoading = false) }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        it.message ?: "Error fetching transactions"
                    )
                )
            }

    }

    private fun fetchAccountDetails() {
        setState { copy(isBalanceLoading = true) }
        viewModelScope.launch {
            accountRepository.getAccountDetails()
                .onSuccess { accountDetails ->
                    setState {
                        copy(
                            isBalanceLoading = false,
                            balance = accountDetails.balance.toString(),
                            isBalanceVisible = true
                        )
                    }
                    // Hide the balance after 5 seconds
                    delay(5_000L)
                    setState {
                        copy(
                            isBalanceVisible = false
                        )
                    }
                }
                .onFailure {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            it.message ?: "Error fetching balance"
                        )
                    )
                }
        }
    }
}

data class HomeScreenState(
    val username: String = "",
    val isBalanceLoading: Boolean = false,
    val isTransactionLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val isBalanceVisible: Boolean = false,
    val balance: String = ""
) : UiState


sealed class HomeScreenEvent : UiEvent {
    data object OnViewBalanceClick : HomeScreenEvent()
}

sealed class HomeScreenEffect : UiEffect {

}