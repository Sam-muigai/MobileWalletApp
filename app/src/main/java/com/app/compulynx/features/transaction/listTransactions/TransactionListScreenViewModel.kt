package com.app.compulynx.features.transaction.listTransactions

import androidx.lifecycle.viewModelScope
import com.app.compulynx.core.base.BaseViewModel
import com.app.compulynx.core.base.UiEffect
import com.app.compulynx.core.base.UiEvent
import com.app.compulynx.core.base.UiState
import com.app.compulynx.domain.models.Transaction
import com.app.compulynx.domain.repositories.TransactionRepository
import com.app.compulynx.utils.format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


typealias ScreenModel = BaseViewModel<TransactionListScreenState, TransactionListScreenEvent, TransactionListScreenEffect>

@HiltViewModel
class TransactionListScreenViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ScreenModel(TransactionListScreenState.Loading) {


    override fun handleEvent(event: TransactionListScreenEvent) {}

    init {
        getTransactions()
    }

    private fun getTransactions() {
        setState { TransactionListScreenState.Loading }
        viewModelScope.launch {
            transactionRepository.getLast100Transactions()
                .onSuccess {
                    val totalAmount = getTotalAmount(it)
                    setState { TransactionListScreenState.Success(it, totalAmount.format()) }
                }.onFailure {
                    setState { TransactionListScreenState.Error(it.message.toString()) }
                }
        }
    }

    private suspend fun getTotalAmount(transactions: List<Transaction>): Double =
        withContext(Dispatchers.Default) {
            var totalAmount = 0.0
            transactions.forEach {
                if (it.debitOrCredit.lowercase() == "credit") {
                    totalAmount += it.amount
                } else {
                    totalAmount -= it.amount
                }
            }
            totalAmount
        }

}


sealed class TransactionListScreenState : UiState {
    object Loading : TransactionListScreenState()
    data class Success(val transactions: List<Transaction>, val totalAmount: String) :
        TransactionListScreenState()
    data class Error(val message: String) : TransactionListScreenState()
}

sealed class TransactionListScreenEvent : UiEvent

sealed class TransactionListScreenEffect : UiEffect