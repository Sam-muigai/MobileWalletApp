package com.app.compulynx.core.network.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    @SerialName("accountNo")
    val accountNo: String?,
    @SerialName("amount")
    val amount: Double?,
    @SerialName("balance")
    val balance: Double?,
    @SerialName("customerId")
    val customerId: String?,
    @SerialName("debitOrCredit")
    val debitOrCredit: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("transactionId")
    val transactionId: String?,
    @SerialName("transactionType")
    val transactionType: String?
)