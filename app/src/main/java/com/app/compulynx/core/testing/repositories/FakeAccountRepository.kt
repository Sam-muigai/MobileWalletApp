package com.app.compulynx.core.testing.repositories

import com.app.compulynx.domain.models.Account
import com.app.compulynx.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAccountRepository : AccountRepository {
    private val _username = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _customerId = MutableStateFlow("")
    private val _accountNumber = MutableStateFlow("")
    private var accountResult: Result<Account> = Result.success(Account())


    override suspend fun getAccountDetails(): Result<Account> = accountResult
    override fun getUsername(): Flow<String> = _username
    override fun getEmail(): Flow<String> = _email
    override fun getCustomerId(): Flow<String> = _customerId
    override fun getAccountNumber(): Flow<String> = _accountNumber


    fun emitUsername(value: String) { _username.value = value }
    fun emitEmail(value: String) { _email.value = value }
    fun emitCustomerId(value: String) { _customerId.value = value }
    fun emitAccountNumber(value: String) { _accountNumber.value = value }

    fun setAccountResult(result: Result<Account>) {
        accountResult = result
    }
}