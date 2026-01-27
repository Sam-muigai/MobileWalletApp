package com.app.compulynx.domain.repositories

import com.app.compulynx.domain.models.Account

interface AccountRepository {

    suspend fun getAccountDetails(): Result<Account>
}