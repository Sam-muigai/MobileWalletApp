package com.app.compulynx.data.mappers

import com.app.compulynx.core.network.dtos.TransactionDto
import com.app.compulynx.domain.models.Transaction

fun TransactionDto.toDomain(): Transaction {
    return Transaction(
        id = id ?: 0,
        accountNo = accountNo ?: "",
        amount = amount ?: 0.0,
        balance = balance ?: 0.0,
        customerId = customerId ?: "",
        debitOrCredit = debitOrCredit ?: "",
        transactionType = transactionType ?: "",
    )
}