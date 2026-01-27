package com.app.compulynx.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.compulynx.R
import com.app.compulynx.core.base.CollectOneTimeEvent
import com.app.compulynx.core.ui.components.LynxButton
import com.app.compulynx.features.home.components.AccountBalanceCard
import com.app.compulynx.features.components.TransactionCard

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onSendMoneyClick: () -> Unit,
    onViewAllTransactionsClick: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val homeScreenState = homeScreenViewModel.state.collectAsStateWithLifecycle().value

    CollectOneTimeEvent(
        homeScreenViewModel.effect
    ) { effect ->
        when (effect) {
            HomeScreenEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    HomeScreenContent(
        homeScreenState = homeScreenState,
        onEvent = homeScreenViewModel::handleEvent,
        onSendMoneyClick = onSendMoneyClick,
        onViewAllTransactionsClick = onViewAllTransactionsClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
    onSendMoneyClick: () -> Unit,
    onViewAllTransactionsClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Welcome ${homeScreenState.username} 👋",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                actions = {
                    Row {
                        IconButton(
                            onClick = {
                                onEvent(HomeScreenEvent.OnLogoutClick)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_logout),
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AccountBalanceCard(
                    isBalanceVisible = homeScreenState.isBalanceVisible,
                    balance = homeScreenState.balance,
                    isBalanceLoading = homeScreenState.isBalanceLoading,
                    onCheckBalanceClick = {
                        onEvent(HomeScreenEvent.OnViewBalanceClick)
                    }
                )
            }
            item {
                LynxButton(
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Send Money")
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(R.drawable.ic_send),
                                contentDescription = null
                            )
                        }
                    },
                    onClick = onSendMoneyClick
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Recent Transactions",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    TextButton(
                        onClick = onViewAllTransactionsClick
                    ) {
                        Text(
                            "View All",
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                    }
                }
            }
            item {
                AnimatedVisibility(homeScreenState.isTransactionLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            }
            items(homeScreenState.transactions) { transaction ->
                TransactionCard(transaction = transaction)
            }
        }
    }
}