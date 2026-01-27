package com.app.compulynx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.compulynx.core.ui.theme.CompuLynxTheme
import com.app.compulynx.features.authentication.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompuLynxTheme {
                LoginScreen(
                    navigateToHome = { },
                    onForgotPassword = { },
                    onSignUp = { },
                    onBackClick = { }
                )
            }
        }
    }
}