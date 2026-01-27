package com.app.compulynx.data.repositories

import com.app.compulynx.core.network.CompulynxApiService
import com.app.compulynx.core.network.dtos.TransactionRequestDto
import com.app.compulynx.data.helpers.mapResult
import com.app.compulynx.data.mappers.toDomain
import com.app.compulynx.domain.models.Transaction
import com.app.compulynx.domain.repositories.TransactionRepository

class TransactionRepositoryImpl(
    private val apiService: CompulynxApiService
) : TransactionRepository {
    override suspend fun getLast100Transactions(customerId: String): Result<List<Transaction>> {
        return apiService.getLast100Transactions(TransactionRequestDto(customerId))
            .mapResult { transactionDtos ->
                transactionDtos?.map { it.toDomain() } ?: emptyList()
            }
    }
}