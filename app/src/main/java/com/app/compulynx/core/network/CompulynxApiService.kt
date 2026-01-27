package com.app.compulynx.core.network

import com.app.compulynx.core.network.dtos.AccountDto
import com.app.compulynx.core.network.dtos.AccountRequestDto
import com.app.compulynx.core.network.dtos.LoginRequestDto
import com.app.compulynx.core.network.dtos.LoginResponseDto
import com.app.compulynx.core.network.helpers.NetworkResult
import com.app.compulynx.core.network.helpers.postRequest
import io.ktor.client.HttpClient

interface CompulynxApiService {

    suspend fun loginUser(loginRequestDto: LoginRequestDto): NetworkResult<LoginResponseDto>

    suspend fun getAccountBalance(accountRequestDto: AccountRequestDto): NetworkResult<AccountDto>
}

class CompulynxApiServiceImpl(private val client: HttpClient) : CompulynxApiService {
    override suspend fun loginUser(loginRequestDto: LoginRequestDto): NetworkResult<LoginResponseDto> {
        return client.postRequest(loginRequestDto, LOGIN)
    }

    override suspend fun getAccountBalance(accountRequestDto: AccountRequestDto): NetworkResult<AccountDto> {
        return client.postRequest(accountRequestDto, GET_ACCOUNT_DETAILS)
    }

    companion object {
        const val BASE_URL = "http://192.168.1.107:8092/springboot-rest-api"
        const val LOGIN = "${BASE_URL}/api/v1/customers/login"

        const val GET_ACCOUNT_DETAILS = "${BASE_URL}/api/v1/accounts/balance"
    }
}