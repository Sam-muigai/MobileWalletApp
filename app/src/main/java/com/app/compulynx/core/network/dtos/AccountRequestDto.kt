package com.app.compulynx.core.network.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountRequestDto(
    @SerialName("customerId")
    val customerId: String?
)