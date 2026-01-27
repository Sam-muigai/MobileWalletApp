package com.app.compulynx.core.network.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("account")
    val accountDto: AccountDto?,
    @SerialName("customerId")
    val customerId: String?,
    @SerialName("customerName")
    val customerName: String?,
    @SerialName("email")
    val email: String?
)