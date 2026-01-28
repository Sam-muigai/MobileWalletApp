package com.app.compulynx.core.testing.repositories

import com.app.compulynx.domain.models.LoginRequest
import com.app.compulynx.domain.repositories.AuthRepository

class FakeAuthRepository : AuthRepository {

    private var shouldReturnError = false
    private var errorMessage = "Invalid credentials"
    private var successMessage = "Login Successful"

    var logoutCalled = false
        private set

    override suspend fun login(loginRequest: LoginRequest): Result<String> {
        return if (shouldReturnError) {
            Result.failure(Exception(errorMessage))
        } else {
            Result.success(successMessage)
        }
    }

    override suspend fun logout() {
        logoutCalled = true
    }


    fun setShouldReturnError(value: Boolean, message: String = "Invalid credentials") {
        shouldReturnError = value
        errorMessage = message
    }
}