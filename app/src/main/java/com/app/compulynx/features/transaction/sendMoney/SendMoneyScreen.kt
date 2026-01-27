package com.app.compulynx.features.transaction.sendMoney

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.compulynx.core.ui.components.LynxButton
import com.app.compulynx.core.ui.components.LynxTextField
import com.app.compulynx.features.authentication.login.LoginScreenEvent

@Composable
fun SendMoneyScreen(
    sendMoneyScreenViewModel: SendMoneyScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val sendMoneyScreenState = sendMoneyScreenViewModel.state.collectAsStateWithLifecycle().value
    SendMoneyScreenContent(
        sendMoneyScreenState = sendMoneyScreenState,
        onEvent = sendMoneyScreenViewModel::handleEvent,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreenContent(
    modifier: Modifier = Modifier,
    sendMoneyScreenState: SendMoneyScreenState,
    onEvent: (SendMoneyScreenEvent) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        "Send Money",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            LynxTextField(
                label = "Account to",
                value = sendMoneyScreenState.accountTo,
                onValueChange = {
                    onEvent(SendMoneyScreenEvent.OnAccountToChanged(it))
                },
                placeholder = "Enter the account number to send money to",
                enabled = !sendMoneyScreenState.isLoading,
                errorText = sendMoneyScreenState.accountToError
            )
            Spacer(Modifier.height(24.dp))
            LynxTextField(
                label = "Amount",
                value = sendMoneyScreenState.amount,
                onValueChange = {
                    onEvent(SendMoneyScreenEvent.OnAmountChanged(it))
                },
                placeholder = "Enter the amount to send",
                enabled = !sendMoneyScreenState.isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                errorText = sendMoneyScreenState.amountError
            )
            Spacer(Modifier.height(24.dp))
            LynxButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(SendMoneyScreenEvent.OnSendMoneyClicked)
                },
                content = {
                    AnimatedContent(
                        sendMoneyScreenState.isLoading
                    ) { loading ->
                        if (loading) {
                            CircularProgressIndicator(
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                "Send Money",
                            )
                        }
                    }
                },
                enabled = sendMoneyScreenState.isFormValid && !sendMoneyScreenState.isLoading
            )
        }
    }
}