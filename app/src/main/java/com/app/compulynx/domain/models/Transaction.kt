package com.app.compulynx.domain.models

import kotlinx.serialization.SerialName

data class Transaction(
    val accountNo: String = "",
    val amount: Double = 0.0,
    val balance: Double = 0.0,
    val customerId: String = "",
    val debitOrCredit: String = "",
    val id: Int = 0,
    val transactionId: String = "",
    val transactionType: String = ""
)
