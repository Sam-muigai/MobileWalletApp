package com.app.compulynx.data.repositories

import com.app.compulynx.core.network.CompulynxApiService
import com.app.compulynx.data.helpers.mapResult
import com.app.compulynx.data.mappers.toDomain
import com.app.compulynx.domain.models.LoginRequest
import com.app.compulynx.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: CompulynxApiService
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<String> {
        return apiService.loginUser(loginRequestDto = loginRequest.toDomain())
            .mapResult { _ ->
                // TODO: Save email and name
                "Login successful"
            }
    }
}