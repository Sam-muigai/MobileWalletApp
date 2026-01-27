package com.app.compulynx.domain.repositories

import com.app.compulynx.domain.models.LoginRequest

interface AuthRepository {
    /**
     * We don't need any data on the UI therefore we can just return a string with a success message
     */
    suspend fun login(loginRequest: LoginRequest): Result<String>

    suspend fun logout()
}