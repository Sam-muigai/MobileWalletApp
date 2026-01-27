package com.app.compulynx.features.profile.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.app.compulynx.features.profile.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
data object Profile : NavKey

fun EntryProviderScope<NavKey>.profileEntry(backStack: NavBackStack<NavKey>) {
    entry<Profile> {
        ProfileScreen(
            onBackClick = {
                backStack.removeLastOrNull()
            }
        )
    }
}