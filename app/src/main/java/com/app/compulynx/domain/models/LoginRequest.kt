package com.app.compulynx.domain.models

data class LoginRequest(
    val customerId: String,
    val pin: String
)
