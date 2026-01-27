package com.app.compulynx.features.authentication.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.compulynx.core.base.CollectOneTimeEvent
import com.app.compulynx.core.ui.components.LynxButton
import com.app.compulynx.core.ui.components.LynxPinTextField
import com.app.compulynx.core.ui.components.LynxTextField
import com.app.compulynx.features.authentication.components.CustomAppBar
import com.app.compulynx.features.authentication.components.HeadlineText

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    onBackClick: () -> Unit
) {
    val loginScreenState = loginScreenViewModel.state.collectAsStateWithLifecycle().value

    CollectOneTimeEvent(loginScreenViewModel.effect) { effect ->
        when (effect) {
            LoginScreenEffect.NavigateToHome -> navigateToHome()
        }
    }

    LoginScreenContent(
        loginScreenState = loginScreenState,
        onEvent = loginScreenViewModel::handleEvent,
        onForgotPassword = onForgotPassword,
        onSignUp = onSignUp,
        onBackClick = onBackClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    loginScreenState: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CustomAppBar(onBackClick = onBackClick)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            HeadlineText(text = "Log In")
            Spacer(Modifier.height(24.dp))
            LynxTextField(
                label = "Customer ID",
                value = loginScreenState.customerId,
                onValueChange = {
                    onEvent(LoginScreenEvent.OnCustomerIdChange(it))
                },
                placeholder = "Enter your customer ID",
                enabled = !loginScreenState.isLoading,
                errorText = loginScreenState.customerIdError
            )
            Spacer(Modifier.height(24.dp))
            LynxPinTextField(
                label = "Pin",
                value = loginScreenState.pin,
                onValueChange = {
                    onEvent(LoginScreenEvent.OnPinChange(it))
                },
                onPasswordVisibilityToggle = {
                    onEvent(LoginScreenEvent.OnPinVisibleChange)
                },
                isPasswordVisible = loginScreenState.isPinVisible,
                placeholder = "****",
                enabled = !loginScreenState.isLoading,
                errorText = loginScreenState.pinError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            )
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable(onClick = onForgotPassword),
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textDecoration = TextDecoration.Underline
            )
            Spacer(Modifier.height(24.dp))
            LynxButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(LoginScreenEvent.OnLoginButtonClick)
                },
                content = {
                    AnimatedContent(
                        loginScreenState.isLoading
                    ) { loading ->
                        if (loading) {
                            CircularProgressIndicator(
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                "Login",
                            )
                        }
                    }
                },
                enabled = loginScreenState.isFormValid && !loginScreenState.isLoading
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    modifier = Modifier.clickable(onClick = onSignUp),
                    text = "Create one",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}