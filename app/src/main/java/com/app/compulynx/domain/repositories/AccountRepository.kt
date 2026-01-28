package com.app.compulynx.domain.repositories

import com.app.compulynx.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getAccountDetails(): Result<Account>
    fun getUsername(): Flow<String>
    fun getEmail(): Flow<String>
    fun getCustomerId(): Flow<String>
    fun getAccountNumber(): Flow<String>
}