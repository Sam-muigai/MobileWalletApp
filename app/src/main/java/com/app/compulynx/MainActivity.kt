package com.app.compulynx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.app.compulynx.core.base.CollectOneTimeEvent
import com.app.compulynx.core.base.SnackbarController
import com.app.compulynx.core.ui.theme.CompuLynxTheme
import com.app.compulynx.features.authentication.login.LoginScreen
import com.app.compulynx.features.authentication.navigation.Login
import com.app.compulynx.features.authentication.navigation.authenticationEntry
import com.app.compulynx.features.home.navigation.homeEntry
import com.app.compulynx.features.profile.navigation.profileEntry
import com.app.compulynx.features.transaction.navigation.transactionEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompuLynxTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(Login)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    CollectOneTimeEvent(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { _ ->
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                authenticationEntry(backStack)
                homeEntry(backStack)
                transactionEntry(backStack)
                profileEntry(backStack)
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            )
        )
    }
}