package com.app.compulynx.domain.repositories

import com.app.compulynx.domain.models.Transaction

interface TransactionRepository {
    suspend fun getLast100Transactions(customerId: String): Result<List<Transaction>>
}