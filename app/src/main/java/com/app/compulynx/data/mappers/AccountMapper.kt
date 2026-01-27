package com.app.compulynx.data.mappers

import com.app.compulynx.core.network.dtos.AccountDto
import com.app.compulynx.domain.models.Account

fun AccountDto.toDomain(): Account {
    return Account(
        accountNo = accountNo ?: "",
        balance = balance ?: 0.0,
    )
}