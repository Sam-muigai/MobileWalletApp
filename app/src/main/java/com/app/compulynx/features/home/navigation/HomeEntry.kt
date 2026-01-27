package com.app.compulynx.features.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.compulynx.features.home.HomeScreen
import com.app.compulynx.features.transaction.navigation.SendMoney
import com.app.compulynx.features.transaction.navigation.TransactionList
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

fun EntryProviderScope<NavKey>.homeEntry(backStack: NavBackStack<NavKey>) {
    entry<Home> {
        HomeScreen(
            onSendMoneyClick = {
                backStack.add(SendMoney)
            },
            onViewAllTransactionsClick = {
                backStack.add(TransactionList)
            }
        )
    }
}