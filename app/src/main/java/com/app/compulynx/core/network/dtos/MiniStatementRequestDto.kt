package com.app.compulynx.core.network.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MiniStatementRequestDto(
    @SerialName("accountNo")
    val accountNo: String?,
    @SerialName("customerId")
    val customerId: String?
)