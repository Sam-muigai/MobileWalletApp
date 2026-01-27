package com.app.compulynx.features.transaction.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.compulynx.features.transaction.listTransactions.TransactionListScreen
import com.app.compulynx.features.transaction.sendMoney.SendMoneyScreen
import kotlinx.serialization.Serializable

@Serializable
data object SendMoney : NavKey

@Serializable
data object TransactionList : NavKey

fun EntryProviderScope<NavKey>.transactionEntry(backStack: NavBackStack<NavKey>) {
    entry<TransactionList> {
        TransactionListScreen(
            onBackClick = {
                backStack.removeLastOrNull()
            }
        )
    }

    entry<SendMoney> {
        SendMoneyScreen {

        }
    }
}
