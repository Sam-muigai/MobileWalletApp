package com.app.compulynx.data.repositories

import com.app.compulynx.core.database.daos.LocalTransactionDao
import com.app.compulynx.core.database.entities.LocalTransactionEntity
import com.app.compulynx.core.database.entities.SyncStatus
import com.app.compulynx.core.datastore.CompulynxPreferences
import com.app.compulynx.core.network.CompulynxApiService
import com.app.compulynx.core.network.dtos.MiniStatementRequestDto
import com.app.compulynx.core.network.dtos.SendMoneyRequestDto
import com.app.compulynx.core.network.dtos.TransactionRequestDto
import com.app.compulynx.core.network.helpers.NetworkResult
import com.app.compulynx.data.helpers.mapResult
import com.app.compulynx.data.mappers.toDomain
import com.app.compulynx.domain.models.SendMoneyRequest
import com.app.compulynx.domain.models.Transaction
import com.app.compulynx.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: CompulynxApiService,
    private val compulynxPreferences: CompulynxPreferences,
    private val localTransactionDao: LocalTransactionDao
) : TransactionRepository {
    override suspend fun getLast100Transactions(): Result<List<Transaction>> {
        val customerId = compulynxPreferences.getCustomerId().first()
        return apiService.getLast100Transactions(TransactionRequestDto(customerId))
            .mapResult { transactionDtos ->
                transactionDtos?.map { it.toDomain() } ?: emptyList()
            }
    }

    override suspend fun getMiniStatement(): Result<List<Transaction>> {
        val customerId = compulynxPreferences.getCustomerId().first()
        val accountNumber = compulynxPreferences.getAccountNumber().first()
        val miniStatementRequestDto =
            MiniStatementRequestDto(accountNo = accountNumber, customerId = customerId)
        return apiService.getMiniStatement(miniStatementRequestDto)
            .mapResult { transactionDtos ->
                transactionDtos?.map { it.toDomain() } ?: emptyList()
            }
    }

    override suspend fun saveLocalTransaction(sendMoneyRequest: SendMoneyRequest) {
        val localTransaction = LocalTransactionEntity(
            clientTransactionId = UUID.randomUUID(),
            accountFrom = compulynxPreferences.getAccountNumber().first(),
            accountTo = sendMoneyRequest.accountTo,
            createdAt = LocalDateTime.now(),
            syncStatus = SyncStatus.QUEUED,
            amount = sendMoneyRequest.amount.toDouble()
        )
        localTransactionDao.saveTransaction(localTransaction)
    }

    override suspend fun syncLocalTransactions() {
        val localTransactions = localTransactionDao.getQueuedTransactions().first()
        sendMoneyRequestToBackend(localTransactions)
    }

    private suspend fun sendMoneyRequestToBackend(
        localTransactions: List<LocalTransactionEntity>
    ) = supervisorScope {
        val customerId = compulynxPreferences.getCustomerId().first()
        localTransactions.forEach { transactionEntity ->
            launch {
                localTransactionDao.updateSyncStatus(transactionEntity.id, SyncStatus.SYNCING)
                val sendMoneyRequest = SendMoneyRequestDto(
                    accountFrom = transactionEntity.accountFrom,
                    accountTo = transactionEntity.accountTo,
                    amount = transactionEntity.amount.toInt(),
                    customerId = customerId
                )
                sendMoney(sendMoneyRequest, transactionEntity)
            }
        }
    }

    private suspend fun sendMoney(
        sendMoneyRequestDto: SendMoneyRequestDto,
        transactionEntity: LocalTransactionEntity
    ) {
        when (val response = apiService.sendMoney(sendMoneyRequestDto)) {
            is NetworkResult.Error -> {
                localTransactionDao.updateSyncStatus(transactionEntity.id, SyncStatus.FAILED)
            }

            is NetworkResult.Success -> {
                if (response.data?.responseStatus == true) {
                    localTransactionDao.updateSyncStatus(transactionEntity.id, SyncStatus.SYNCED)
                } else {
                    localTransactionDao.updateSyncStatus(transactionEntity.id, SyncStatus.FAILED)
                }
            }
        }
    }
}