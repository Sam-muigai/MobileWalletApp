package com.app.compulynx.data.repositories

import com.app.compulynx.core.datastore.CompulynxPreferences
import com.app.compulynx.core.network.CompulynxApiService
import com.app.compulynx.data.helpers.mapResult
import com.app.compulynx.data.mappers.toDomain
import com.app.compulynx.domain.models.LoginRequest
import com.app.compulynx.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: CompulynxApiService,
    private val compulynxPreferences: CompulynxPreferences
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<String> {
        return apiService.loginUser(loginRequestDto = loginRequest.toDomain())
            .mapResult { responseDto ->
                responseDto?.email?.let {
                    compulynxPreferences.saveEmail(responseDto.email)
                }
                responseDto?.customerId?.let {
                    compulynxPreferences.saveCustomerId(responseDto.customerId)
                }
                responseDto?.accountDto?.let { account ->
                    compulynxPreferences.saveAccountNumber(account.accountNo ?: "")
                }
                responseDto?.customerName?.let {
                    compulynxPreferences.saveName(responseDto.customerName)
                }
                "Login successful"
            }
    }

    override suspend fun logout() {
        compulynxPreferences.clearAll()
    }
}