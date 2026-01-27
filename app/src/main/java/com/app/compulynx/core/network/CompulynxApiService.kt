package com.app.compulynx.core.network

import com.app.compulynx.core.network.dtos.LoginRequestDto
import com.app.compulynx.core.network.dtos.LoginResponseDto
import com.app.compulynx.core.network.helpers.NetworkResult
import com.app.compulynx.core.network.helpers.postRequest
import io.ktor.client.HttpClient

interface CompulynxApiService {

    suspend fun loginUser(loginRequestDto: LoginRequestDto): NetworkResult<LoginResponseDto>
}

class CompulynxApiServiceImpl(private val client: HttpClient) : CompulynxApiService {
    override suspend fun loginUser(loginRequestDto: LoginRequestDto): NetworkResult<LoginResponseDto> {
        return client.postRequest(loginRequestDto, LOGIN)
    }

    companion object {
        const val BASE_URL = "http://localhost:8092/springboot-rest-api"
        const val LOGIN = "${BASE_URL}/api/v1/customers/login"
    }
}