package com.app.compulynx.data.repositories

import com.app.compulynx.core.network.CompulynxApiService
import com.app.compulynx.core.network.dtos.AccountRequestDto
import com.app.compulynx.data.helpers.mapResult
import com.app.compulynx.data.mappers.toDomain
import com.app.compulynx.domain.models.Account
import com.app.compulynx.domain.repositories.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: CompulynxApiService
) : AccountRepository {
    override suspend fun getAccountDetails(): Result<Account> {
        val accountNumber = "1234567890" // Get the value from datastore
        return apiService.getAccountBalance(AccountRequestDto(accountNumber))
            .mapResult { responseDto ->
                responseDto?.toDomain() ?: Account()
            }
    }
}