package com.app.compulynx.core.network.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("accountNo")
    val accountNo: String?,
    @SerialName("balance")
    val balance: Double?
)