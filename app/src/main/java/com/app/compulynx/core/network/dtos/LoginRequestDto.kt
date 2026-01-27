package com.app.compulynx.core.network.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("customerId")
    val customerId: String?,
    @SerialName("pin")
    val pin: String?
)